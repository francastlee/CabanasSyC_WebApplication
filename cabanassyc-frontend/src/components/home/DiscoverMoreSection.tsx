"use client";
import { useTranslation } from "react-i18next";
import { FocusCards } from "../animations/FocusCards";
import { StarsBackground } from "../animations/StarsBackground";
import type { CardType } from "../home/Types";

interface DiscoverMoreSectionProps {
  cards?: CardType[];
}

const DiscoverMoreSection = ({ cards: propCards }: DiscoverMoreSectionProps) => {
  const { t } = useTranslation("home");

  const defaultCards: CardType[] = [
    {
      title: t("discoverMore.facilitiesTitle"),
      subtitle: t("discoverMore.facilitiesSubtitle"),
      src: "/imgs/home/facilities.jpg",
    },
    {
      title: t("discoverMore.toursTitle"),
      subtitle: t("discoverMore.toursSubtitle"),
      src: "/imgs/home/tours.jpg",
    },
    {
      title: t("discoverMore.roomsTitle"),
      subtitle: t("discoverMore.roomsSubtitle"),
      src: "/imgs/home/cabin2.webp",
    },
  ];

  const cards = propCards || defaultCards;

  return (
    <section 
      className="bg-[#4B2A1F] w-full relative overflow-hidden h-[150vh] xl:h-screen select-none"
      aria-labelledby="discover-more-title"
    >
      <div 
        className="absolute bottom-0 left-0 w-full h-40 bg-gradient-to-t from-[#1a3a17] to-transparent pointer-events-none z-10"
        aria-hidden="true"
      />
      <StarsBackground starCount={100} />
      
      <div className="container mx-auto px-4 relative z-20">
        <h2 
          id="discover-more-title"
          className="text-4xl md:text-5xl lg:text-6xl text-white text-center mb-12 font-cinzel text-glow-green mt-12"
        >
          {t("discoverMore.title")}
        </h2>

        <FocusCards cards={cards} />
      </div>
    </section>
  );
};

export default DiscoverMoreSection;