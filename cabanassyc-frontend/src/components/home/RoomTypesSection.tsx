"use client";

import { CabinCard } from "../common/CabinCard";
import AnimatedContent from "../animations/AnimatedContent";
import CinematicImage from "../animations/CinematicImage";
import { useTranslation } from "react-i18next";

const RoomTypesSection = () => {
  const { t } = useTranslation("home"); 
  return (
    <section className="relative bg-[#1a3a17] min-h-screen overflow-hidden flex flex-col justify-center items-center w-full px-4 md:px-8 select-none">
      <div className="absolute bottom-0 left-0 w-full h-32 md:h-40 bg-gradient-to-t from-[#4B2A1F] to-transparent pointer-events-none z-20"></div>
      <CinematicImage src="/imgs/home/slothTree.png" variant="sloth" />
      <CinematicImage src="/imgs/home/nature.png" variant="nature" />

      
      <div className="relative z-10 flex flex-col items-center w-full max-w-7xl mx-auto">
        <h2 className="text-white text-4xl md:text-6xl font-cinzel text-center mb-10 font-semibold text-glow-brown leading-tight">
          {t("roomTypes.title")}
        </h2>

        <div className="flex flex-col md:flex-row flex-wrap justify-center items-center gap-10 md:gap-20 w-full px-4 ">
          <AnimatedContent
            distance={300}
            direction="horizontal"
            reverse={true}
            config={{ tension: 40, friction: 20 }}
            initialOpacity={0}
            animateOpacity
            scale={1.1}
            threshold={0.2}
          >
            <CabinCard
              title={t("roomTypes.subtitletTripleRoom")}
              description={t("roomTypes.descriptionTripleRoom")}
              imageUrl="/imgs/home/cabin2.jpg"
            />
          </AnimatedContent>

          <AnimatedContent
            distance={300}
            direction="horizontal"
            reverse={false}
            config={{ tension: 40, friction: 20 }}
            initialOpacity={0}
            animateOpacity
            scale={1.1}
            threshold={0.2}
          >
            <CabinCard
              title={t("roomTypes.subtitleDobleRoom")}
              description={t("roomTypes.descriptionDobleRoom")}
              imageUrl="/imgs/home/cabin2.jpg"
            />
          </AnimatedContent>
        </div>
      </div>
    </section>
  );
};

export default RoomTypesSection;