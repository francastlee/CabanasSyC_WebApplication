import { useTranslation } from "react-i18next";
import { AnimatedButton } from "../common/AnimatedButton";
import { FaLongArrowAltRight } from "react-icons/fa";

export const HeroSection = () => {
  const { t } = useTranslation("home"); 

  return (
    <section className="relative flex flex-col justify-center items-center text-center text-white h-screen bg-black/40 overflow-hidden w-full select-none">
      <div className="absolute bottom-0 left-0 w-full h-40 bg-gradient-to-t from-[#1a3a17] to-transparent pointer-events-none"></div>

      <h1 className="text-4xl md:text-6xl mb-4 z-10 font-cinzel font-semibold text-glow-brown">
        {t("hero.title")}
      </h1>
      <p className="text-lg md:text-2xl font-cinzel mb-8 animate-fade-in-up z-10 text-glow-green">
        {t("hero.subtitle")}
      </p>
    
      <a
        href="https://www.booking.com/Share-Q4pFasj"
        target="_blank"
        rel="noopener noreferrer"
        aria-label="Sitio del desarrollador"
      >
        <AnimatedButton icon={<FaLongArrowAltRight />}>
          {t("hero.button")}
        </AnimatedButton>
      </a>

          
    </section>
  );
};
