import { useEffect, useState } from "react";
import { FaEnvelope, FaLock, FaUser } from "react-icons/fa";
import { toast } from "react-toastify";
import { useNavigate, Link } from "react-router-dom";
import SplitText from "../../components/animations/SplitText";
import LanguageSwitcher from "../../components/navbar/LanguageSwitcher";
import { useTranslation } from "react-i18next";
import { useAuth } from "../../hooks/UseAuth";
import { registerRequest } from "../../api/Auth";

type RegisterPayload = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  hourlyRate: number;
};

const Register = () => {
  const { t } = useTranslation("register");
  const navigate = useNavigate();
  const { authenticated, user, loginWithToken } = useAuth();

  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);
  const [loading, setLoading] = useState(false);

  const [errors, setErrors] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");
    if (!token) return;

    loginWithToken(token);

    const url = new URL(window.location.href);
    url.searchParams.delete("token");
    window.history.replaceState({}, document.title, url.toString());
  }, [loginWithToken]);

  useEffect(() => {
    if (authenticated && user) {
      if (user.role === "ADMIN") navigate("/admin/dashboard");
      else if (user.role === "WORKER") navigate("/worker/dashboard");
      else navigate("/home");
    }
  }, [authenticated, user, navigate]);

  const validateEmail = (email: string) => /\S+@\S+\.\S+/.test(email);
  const validateName = (v: string) => v.trim().length >= 2;
  const validatePassword = (password: string) => password.trim().length >= 4;

  const validateField = (name: keyof typeof form, value: string) => {
    switch (name) {
      case "firstName":
        return validateName(value) ? "" : t("errors.firstName");
      case "lastName":
        return validateName(value) ? "" : t("errors.lastName");
      case "email":
        return validateEmail(value) ? "" : t("errors.email");
      case "password":
        return validatePassword(value) ? "" : t("errors.password");
      case "confirmPassword":
        return value === form.password ? "" : t("errors.confirmPassword");
      default:
        return "";
    }
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement>
  ) => {
    const { name, value } = e.target as { name: keyof typeof form; value: string };
    setForm(prev => ({ ...prev, [name]: value }));

    const message = validateField(name, value);
    setErrors(prev => ({ ...prev, [name]: message }));

    if (name === "password" && form.confirmPassword) {
      const confirmMsg = value === form.confirmPassword ? "" : t("errors.confirmPassword");
      setErrors(prev => ({ ...prev, confirmPassword: confirmMsg }));
    }
  };

  const validateAll = () => {
    const newErrors = {
      firstName: validateField("firstName", form.firstName),
      lastName: validateField("lastName", form.lastName),
      email: validateField("email", form.email),
      password: validateField("password", form.password),
      confirmPassword: validateField("confirmPassword", form.confirmPassword),
    };
    setErrors(newErrors);
    return Object.values(newErrors).every(v => v === "");
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!validateAll()) {
      toast.error(t("messages.fixErrors"));
      return;
    }

    setLoading(true);
    try {
      const payload: RegisterPayload = {
      firstName: form.firstName.trim(),
      lastName: form.lastName.trim(),
      email: form.email.trim(),
      password: form.password,
      hourlyRate: 0,
      };

      await registerRequest(payload);

      toast.success(t("messages.registerSuccess"));
      navigate("/login");
    } catch (err: any) {
      toast.error(err?.message || t("messages.registerError"));
    } finally {
      setLoading(false);
    }
  };


  const ringOk = "ring-2 ring-[#B28B09]";
  const ringErr = "ring-2 ring-[#B8512D]";

  return (
    <section
      className="flex justify-center items-center w-full min-h-screen bg-cover bg-center p-6 sm:p-8 animate-[swayBackground_5s_ease-in]"
      style={{ backgroundImage: "url('/imgs/login/bglogin.webp')" }}
    >
      <div className="absolute top-5 right-20 z-50">
        <LanguageSwitcher />
      </div>

      <div className="flex flex-col items-center w-full max-w-md bg-[#4B2A1F]/[.80] rounded-3xl shadow-lg p-6 sm:p-8 animate-fadeIn">
        <a href="/">
          <img
            className="w-20 mb-4 hover:scale-110 transition-transform duration-500"
            src="/imgs/common/logoSyC.png"
            alt="Logo"
          />
        </a>

        <SplitText
          text={t("title")}
          className="text-[#B28B09] font-serif text-2xl sm:text-3xl mb-6 select-none"
          delay={80}
        />
        <SplitText
          text={t("subtitle")}
          className="text-[#B28B09] font-serif text-lg mb-6 select-none"
          delay={125}
        />

        <form className="w-full flex flex-col items-center gap-4" onSubmit={handleSubmit}>
          <div className="w-full">
            <div className="relative w-full">
              <span className="absolute inset-y-0 left-0 flex items-center justify-center w-12 bg-[#4B2A1F] rounded-l-full">
                <FaUser className="text-white" />
              </span>
              <input
                type="text"
                name="firstName"
                placeholder={t("placeholders.firstName")}
                disabled={loading}
                value={form.firstName}
                onChange={handleChange}
                className={`pl-16 pr-4 py-2 w-full rounded-full outline-none text-sm bg-[#856D5D]/[.80] placeholder-white text-white ${errors.firstName ? ringErr : ringOk}`}
                required
                autoComplete="given-name"
              />
            </div>
            <p className="text-[#B8512D] text-sm mt-1 min-h-[20px]">{errors.firstName}</p>
          </div>

          <div className="w-full">
            <div className="relative w-full">
              <span className="absolute inset-y-0 left-0 flex items-center justify-center w-12 bg-[#4B2A1F] rounded-l-full">
                <FaUser className="text-white" />
              </span>
              <input
                type="text"
                name="lastName"
                placeholder={t("placeholders.lastName")}
                disabled={loading}
                value={form.lastName}
                onChange={handleChange}
                className={`pl-16 pr-4 py-2 w-full rounded-full outline-none text-sm bg-[#856D5D]/[.80] placeholder-white text-white ${errors.lastName ? ringErr : ringOk}`}
                required
                autoComplete="family-name"
              />
            </div>
            <p className="text-[#B8512D] text-sm mt-1 min-h-[20px]">{errors.lastName}</p>
          </div>

          {/* Email */}
          <div className="w-full">
            <div className="relative w-full">
              <span className="absolute inset-y-0 left-0 flex items-center justify-center w-12 bg-[#4B2A1F] rounded-l-full">
                <FaEnvelope className="text-white" />
              </span>
              <input
                type="email"
                name="email"
                placeholder={t("placeholders.email")}
                disabled={loading}
                value={form.email}
                onChange={handleChange}
                className={`pl-16 pr-4 py-2 w-full rounded-full outline-none text-sm bg-[#856D5D]/[.80] placeholder-white text-white ${errors.email ? ringErr : ringOk}`}
                required
                autoComplete="email"
              />
            </div>
            <p className="text-[#B8512D] text-sm mt-1 min-h-[20px]">{errors.email}</p>
          </div>

          {/* Password */}
          <div className="w-full">
            <div className="relative w-full">
              <span className="absolute inset-y-0 left-0 flex items-center justify-center w-12 bg-[#4B2A1F] rounded-l-full">
                <FaLock className="text-white" />
              </span>
              <input
                type={showPassword ? "text" : "password"}
                name="password"
                placeholder={t("placeholders.password")}
                disabled={loading}
                value={form.password}
                onChange={handleChange}
                className={`pl-16 pr-10 py-2 w-full rounded-full outline-none text-sm bg-[#856D5D]/[.80] placeholder-white text-white ${errors.password ? ringErr : ringOk}`}
                required
                autoComplete="new-password"
              />
              <button
                type="button"
                onClick={() => setShowPassword(prev => !prev)}
                className="absolute inset-y-0 right-3 flex items-center text-white hover:text-yellow-300 cursor-pointer"
                aria-label={showPassword ? t("aria.hidePassword") : t("aria.showPassword")}
              >
                {showPassword ? "🙈" : "👁️"}
              </button>
            </div>
            <p className="text-[#B8512D] text-sm mt-1 min-h-[20px]">{errors.password}</p>
          </div>

          {/* Confirm Password */}
          <div className="w-full">
            <div className="relative w-full">
              <span className="absolute inset-y-0 left-0 flex items-center justify-center w-12 bg-[#4B2A1F] rounded-l-full">
                <FaLock className="text-white" />
              </span>
              <input
                type={showConfirm ? "text" : "password"}
                name="confirmPassword"
                placeholder={t("placeholders.confirmPassword")}
                disabled={loading}
                value={form.confirmPassword}
                onChange={handleChange}
                className={`pl-16 pr-10 py-2 w-full rounded-full outline-none text-sm bg-[#856D5D]/[.80] placeholder-white text-white ${errors.confirmPassword ? ringErr : ringOk}`}
                required
                autoComplete="new-password"
              />
              <button
                type="button"
                onClick={() => setShowConfirm(prev => !prev)}
                className="absolute inset-y-0 right-3 flex items-center text-white hover:text-yellow-300 cursor-pointer"
                aria-label={showConfirm ? t("aria.hidePassword") : t("aria.showPassword")}
              >
                {showConfirm ? "🙈" : "👁️"}
              </button>
            </div>
            <p className="text-[#B8512D] text-sm mt-1 min-h-[20px]">{errors.confirmPassword}</p>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="rounded-full bg-[#856D5D] py-2 px-6 text-white w-1/2 hover:bg-[#6c5247] transition mt-2 text-sm select-none cursor-pointer"
          >
            {loading ? (
              <div className="flex items-center justify-center">
                <svg className="animate-spin h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8H4z"></path>
                </svg>
              </div>
            ) : t("buttons.signUp")}
          </button>
        </form>

        <div className="flex items-center my-6 w-full">
          <div className="h-[1px] w-full bg-white"></div>
          <span className="mx-2 text-white text-sm select-none">{t("or")}</span>
          <div className="h-[1px] w-full bg-white"></div>
        </div>

        <span className="text-white text-sm select-none">{t("alreadyHaveAccount")}</span>
        <Link
          to="/login"
          className="text-white mt-2 text-xs hover:text-[#B28B09] cursor-pointer select-none"
        >
          {t("goToLogin")}
        </Link>
      </div>
    </section>
  );
};

export default Register;
