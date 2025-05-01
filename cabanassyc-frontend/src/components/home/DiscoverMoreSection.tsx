"use client";

import { useTranslation } from "react-i18next";
import { FocusCards } from "../animations/FocusCards";

const DiscoverMoreSection = () => {
  const { t } = useTranslation("home");

  const cards = [
    {
      title: t("discoverMore.facilitiesTitle"),
      subtitle: t("discoverMore.facilitiesSubtitle"),
      src: "./imgs/home/facilities.jpg",
    },
    {
      title: t("discoverMore.toursTitle"),
      subtitle: t("discoverMore.toursSubtitle"),
      src: "./imgs/home/tours.jpg",
    },
    {
      title: t("discoverMore.roomsTitle"),
      subtitle: t("discoverMore.roomsSubtitle"),
      src: "./imgs/home/cabin2.jpg",
    },
  ];
  

  return (
    <section className="bg-[#4B2A1F] w-full min-h-[130vh] relative overflow-x-hidden">
      <div className="absolute bottom-0 left-0 w-full h-40 bg-gradient-to-t from-[#1a3a17] to-transparent pointer-events-none"></div>

      <div className="hidden lg:block stars z-0 absolute inset-0 pointer-events-none">
        {Array.from({ length: 100 }).map((_, i) => {
          const size = Math.random() * 2 + 1;
          return (
            <div
              key={i}
              className="star"
              style={{
                top: `${Math.random() * 100}%`,
                left: `${Math.random() * 100}%`,
                width: `${size}px`,
                height: `${size}px`,
                animationDelay: `${Math.random() * 6}s`,
                opacity: Math.random() * 0.5 + 0.3,
              }}
            />
          );
        })}
      </div>

      <h2 className="text-4xl md:text-5xl lg:text-6xl pt-20 text-white text-center mb-12 font-cinzel text-glow-green z-20 relative select-none">
        {t("discoverMore.title")}
      </h2>

      <div className="relative z-20 px-4">
        <FocusCards cards={cards} />
      </div>
    </section>
  );
};

export default DiscoverMoreSection;
