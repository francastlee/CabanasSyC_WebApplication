import { useEffect, useState } from "react";
import { FaEnvelope, FaLock, FaGoogle, FaInstagram } from "react-icons/fa";
import { toast } from "react-toastify";
import { useAuth } from "../../hooks/UseAuth";
import { useNavigate } from "react-router-dom";
import SplitText from "../../components/animations/SplitText";
import LanguageSwitcher from "../../components/navbar/LanguageSwitcher";
import { useTranslation } from "react-i18next";

const Login = () => {
  const { t } = useTranslation("login");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [rememberMe, setRememberMe] = useState(false);
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const { login, authenticated, user, loginWithToken } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const storedEmail = localStorage.getItem("rememberedEmail");
    if (storedEmail) {
      setEmail(storedEmail);
      setRememberMe(true);
    }
  }, []);

  useEffect(() => {
    if (authenticated && user) {
      if (user.role === "ADMIN") navigate("/admin/dashboard");
      else if (user.role === "WORKER") navigate("/worker/dashboard");
      else navigate("/home");
    }
  }, [authenticated, user, navigate]);

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");
    if (!token) return;

    // usa tu AuthContext
    loginWithToken(token);

    // limpiar el query param
    const url = new URL(window.location.href);
    url.searchParams.delete("token");
    window.history.replaceState({}, document.title, url.toString());
    // no navegamos aquí; tu AuthContext ya debe navegar según el rol
  }, [loginWithToken]);

  const validateEmail = (email: string) => /\S+@\S+\.\S+/.test(email);
  const validatePassword = (password: string) => password.trim().length >= 4;

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setEmail(value);
    setEmailError(validateEmail(value) ? "" : t("errors.invalidEmail"));
  };

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setPassword(value);
    setPasswordError(validatePassword(value) ? "" : t("errors.invalidPassword"));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!validateEmail(email)) {
      setEmailError(t("errors.invalidEmail"));
      toast.error(t("errors.invalidEmail"));
      return;
    }

    if (!validatePassword(password)) {
      setPasswordError(t("errors.invalidPassword"));
      toast.error(t("errors.invalidPassword"));
      return;
    }

    setLoading(true);

    try {
      await new Promise(resolve => setTimeout(resolve, 800));
      await login(email, password);
      rememberMe
        ? localStorage.setItem("rememberedEmail", email)
        : localStorage.removeItem("rememberedEmail");
      toast.success(t("messages.loginSuccess"));
    } catch {
      toast.error(t("messages.loginError"));
    } finally {
      setLoading(false);
    }
  };

  return (
    <section
      className="flex justify-center items-center w-full min-h-screen bg-cover bg-center p-6 sm:p-8 animate-[swayBackground_5s_ease-in]"
      style={{ backgroundImage: "url('/imgs/login/bglogin.jpg')" }}
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

        <SplitText text={t("title")} className="text-[#B28B09] font-serif text-2xl sm:text-3xl mb-6 select-none" delay={80} />
        <SplitText text={t("subtitle")} className="text-[#B28B09] font-serif text-lg mb-6 select-none" delay={125} />

        <form className="w-full flex flex-col items-center gap-4" onSubmit={handleSubmit}>
          <div className="w-full">
            <div className="relative w-full">
              <span className="absolute inset-y-0 left-0 flex items-center justify-center w-12 bg-[#4B2A1F] rounded-l-full">
                <FaEnvelope className="text-white" />
              </span>
              <input
                type="email"
                placeholder={t("placeholders.email")}
                disabled={loading}
                value={email}
                onChange={handleEmailChange}
                className={`pl-16 pr-4 py-2 w-full rounded-full outline-none text-sm bg-[#856D5D]/[.80] placeholder-white text-white ${emailError ? "ring-2 ring-[#B8512D]" : "ring-2 ring-[#B28B09]"}`}
                required
              />
            </div>
            <p className="text-[#B8512D] text-sm mt-1 min-h-[20px]">{emailError}</p>
          </div>

          <div className="w-full">
            <div className="relative w-full">
              <span className="absolute inset-y-0 left-0 flex items-center justify-center w-12 bg-[#4B2A1F] rounded-l-full">
                <FaLock className="text-white" />
              </span>
              <input
                type={showPassword ? "text" : "password"}
                placeholder={t("placeholders.password")}
                disabled={loading}
                value={password}
                onChange={handlePasswordChange}
                className={`pl-16 pr-10 py-2 w-full rounded-full outline-none text-sm bg-[#856D5D]/[.80] placeholder-white text-white ${passwordError ? "ring-2 ring-[#B8512D]" : "ring-2 ring-[#B28B09]"}`}
                required
              />
              <button
                type="button"
                onClick={() => setShowPassword(prev => !prev)}
                className="absolute inset-y-0 right-3 flex items-center text-white hover:text-yellow-300 cursor-pointer"
              >
                {showPassword ? "🙈" : "👁️"}
              </button>
            </div>
            <p className="text-[#B8512D] text-sm mt-1 min-h-[20px]">{passwordError}</p>
          </div>

          <div className="flex items-center w-full justify-start gap-2 text-white text-sm select-none">
            <input
              type="checkbox"
              id="rememberMe"
              checked={rememberMe}
              onChange={(e) => setRememberMe(e.target.checked)}
              className="accent-yellow-400 cursor-pointer"
              disabled={loading}
            />
            <label htmlFor="rememberMe">{t("options.rememberMe")}</label>
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
            ) : t("buttons.signIn")}
          </button>
        </form>

        <div className="flex items-center my-6 w-full">
          <div className="h-[1px] w-full bg-white"></div>
          <span className="mx-2 text-white text-sm select-none">{t("or")}</span>
          <div className="h-[1px] w-full bg-white"></div>
        </div>

        <span className="text-white mb-4 text-sm select-none">{t("socialLogin")}</span>

        <div className="flex flex-col sm:flex-row gap-4 w-full">
          <a href="http://localhost:8080/oauth2/authorization/google" className="flex items-center bg-[#856D5D] rounded-full py-2 px-6 w-full hover:bg-[#6c5247] transition select-none">
            <FaGoogle className="text-white" />
            <span className="text-white flex-grow px-2 text-center text-sm">{t("buttons.google")}</span>
          </a>

          <button className="flex items-center bg-[#856D5D] rounded-full py-2 px-6 w-full hover:bg-[#6c5247] transition select-none cursor-pointer">
            <FaInstagram className="text-white" />
            <span className="text-white flex-grow px-2 text-center text-sm">{t("buttons.instagram")}</span>
          </button>
        </div>

        <span className="text-white mt-8 text-xs hover:text-[#B28B09] cursor-pointer select-none">
          {t("noAccount")}
        </span>
      </div>
    </section>
  );
};

export default Login; 