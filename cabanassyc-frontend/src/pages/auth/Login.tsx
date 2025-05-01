import { useEffect, useState } from "react";
import { FaEnvelope, FaLock, FaGoogle, FaInstagram } from "react-icons/fa";
import { toast } from "react-toastify";
import { useAuth } from "../../hooks/UseAuth";
import { useNavigate } from "react-router-dom";
import SplitText from "../../components/animations/SplitText";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [rememberMe, setRememberMe] = useState(false);

  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const { login, authenticated, user } = useAuth();
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
      if (user.role === "ADMIN") {
        navigate('/admin/dashboard');
      } else if (user.role === "WORKER") {
        navigate('/worker/dashboard');
      } else {
        navigate('/home');
      }
    }
  }, [authenticated, user, navigate]);

  const validateEmail = (email: string) => /\S+@\S+\.\S+/.test(email);
  const validatePassword = (password: string) => password.trim().length >= 4;

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setEmail(value);

    if (!validateEmail(value)) {
      setEmailError("Correo inválido.");
    } else {
      setEmailError("");
    }
  };

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setPassword(value);

    if (!validatePassword(value)) {
      setPasswordError("Mínimo 4 caracteres.");
    } else {
      setPasswordError("");
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!validateEmail(email)) {
      setEmailError("Correo inválido.");
      toast.error("Correo inválido.");
      return;
    }

    if (!validatePassword(password)) {
      setPasswordError("Mínimo 4 caracteres.");
      toast.error("La contraseña debe tener al menos 4 caracteres.");
      return;
    }

    setLoading(true);

    try {
      await new Promise(resolve => setTimeout(resolve, 800));
      await login(email, password);

      if (rememberMe) {
        localStorage.setItem("rememberedEmail", email);
      } else {
        localStorage.removeItem("rememberedEmail");
      }

      toast.success("Inicio de sesión exitoso 🚀");
    } catch (error) {
      toast.error("Error al iniciar sesión.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="flex justify-center items-center w-full min-h-screen bg-cover bg-center p-6 sm:p-8 animate-[swayBackground_5s_ease-in]" style={{ backgroundImage: "url('/imgs/login/bglogin.jpg')" }}>
      <div className="flex flex-col items-center w-full max-w-md bg-[#4B2A1F]/[.80] rounded-3xl shadow-lg p-6 sm:p-8 animate-fadeIn">

        <a href="/">
          <img className="w-20 mb-4 hover:scale-110 transition-transform duration-500" src="/imgs/common/logoSyC.png" alt="Logo" />
        </a>

        <SplitText 
          text="Welcome to Cabañas SyC"
          className="text-[#B28B09] font-serif text-2xl sm:text-3xl mb-6"
          delay={80}
          animationFrom={{ opacity: 0, transform: "translate3d(0,20px,0)" }}
          animationTo={{ opacity: 1, transform: "translate3d(0,0px,0)" }}
          textAlign="center"
        />
        <SplitText 
          text="Please sign in"
          className="text-[#B28B09] font-serif text-lg sm:text-lg mb-6"
          delay={125}
          animationFrom={{ opacity: 0, transform: "translate3d(0,20px,0)" }}
          animationTo={{ opacity: 1, transform: "translate3d(0,0px,0)" }}
          textAlign="center"
        />

        <form className="w-full flex flex-col items-center gap-4" onSubmit={handleSubmit}>
          
          <div className="w-full">
            <div className="relative w-full">
              <span className="absolute inset-y-0 left-0 flex items-center justify-center w-12 bg-[#4B2A1F] rounded-l-full">
                <FaEnvelope className="text-white" />
              </span>
              <input
                type="email"
                aria-label="Email"
                disabled={loading}
                required
                placeholder="Email"
                value={email}
                onChange={handleEmailChange}
                className={`pl-16 pr-4 py-2 w-full rounded-full outline-none text-sm sm:text-base
                  ${emailError ? 'bg-[#856D5D]/[.80] placeholder-white focus:ring-2 focus:ring-[#B8512D]' : 'bg-[#856D5D]/[.80] placeholder-white focus:ring-2 focus:ring-[#B28B09]'}
                  text-white transition duration-300
                `}
              />
            </div>
            <p className="text-[#B8512D] text-sm mt-1 min-h-[20px] transition-all">
              {emailError}
            </p>
          </div>

          <div className="w-full">
            <div className="relative w-full">
              <span className="absolute inset-y-0 left-0 flex items-center justify-center w-12 bg-[#4B2A1F] rounded-l-full">
                <FaLock className="text-white" />
              </span>
              <input
                type={showPassword ? "text" : "password"}
                aria-label="Password"
                disabled={loading}
                required
                placeholder="Password"
                value={password}
                onChange={handlePasswordChange}
                className={`pl-16 pr-10 py-2 w-full rounded-full outline-none text-sm sm:text-base
                  ${passwordError ? 'bg-[#856D5D]/[.80] placeholder-white focus:ring-2 focus:ring-[#B8512D]' : 'bg-[#856D5D]/[.80] placeholder-white focus:ring-2 focus:ring-[#B28B09]'}
                  text-white transition duration-300
                `}
              />
              <button
                type="button"
                onClick={() => setShowPassword(prev => !prev)}
                className="absolute inset-y-0 right-3 flex items-center text-white hover:text-yellow-300"
                tabIndex={-1}
              >
                {showPassword ? "🙈" : "👁️"}
              </button>
            </div>
            <p className="text-[#B8512D] text-sm mt-1 min-h-[20px] transition-all">
              {passwordError}
            </p>
          </div>

          <div className="flex items-center w-full justify-start gap-2 text-white text-sm mt-2">
            <input
              type="checkbox"
              id="rememberMe"
              checked={rememberMe}
              onChange={(e) => setRememberMe(e.target.checked)}
              className="accent-yellow-400"
              disabled={loading}
            />
            <label htmlFor="rememberMe">Recordarme</label>
          </div>

          <button
            type="submit"
            disabled={loading}
            aria-label="Sign in"
            className="rounded-full bg-[#856D5D] py-2 px-6 text-white w-1/2 hover:bg-[#6c5247] transition duration-300 text-sm sm:text-base mt-2"
          >
            {loading ? (
              <div className="flex items-center justify-center">
                <svg className="animate-spin h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8H4z"></path>
                </svg>
              </div>
            ) : "Sign in"}
          </button>

        </form>

        <div className="flex items-center my-6 w-full">
          <div className="h-[1px] w-full bg-white"></div>
          <span className="mx-2 sm:mx-4 text-white text-sm sm:text-base">Or</span>
          <div className="h-[1px] w-full bg-white"></div>
        </div>

        <span className="text-white mb-4 text-sm sm:text-base">Sign in with</span>

        <div className="flex flex-col sm:flex-row gap-4 w-full sm:w-auto">
          <a href="/oauth2/authorization/google" className="flex items-center bg-[#856D5D] rounded-full py-2 px-6 overflow-hidden w-full sm:w-40 hover:bg-[#6c5247] transition">
            <FaGoogle className="text-white" />
            <span className="text-white flex-grow px-2 text-center text-sm sm:text-base">Google</span>
          </a>

          <button className="flex items-center bg-[#856D5D] rounded-full py-2 px-6 overflow-hidden w-full sm:w-40 hover:bg-[#6c5247] transition">
            <FaInstagram className="text-white" />
            <span className="text-white flex-grow px-2 text-center text-sm sm:text-base">Instagram</span>
          </button>
        </div>

        <span className="text-white mt-8 text-xs sm:text-sm">Don't have an account?</span>

      </div>
    </section>
  );
};

export default Login;
