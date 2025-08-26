"use client";
import React, { useState, useEffect } from "react";
import { cn } from "../../utils/Utils";
import AnimatedContent from "./AnimatedContent";
import type { CardType } from "../home/Types";
import { debounce } from "../../utils/Debounce";

interface CardProps {
  card: CardType;
  index: number;
  hovered: number | null;
  setHovered: React.Dispatch<React.SetStateAction<number | null>>;
  isMobile: boolean;
}

const CardOverlay = ({ hovered, card }: { hovered: boolean; card: CardType }) => (
  <div
    className={cn(
      "absolute inset-0 bg-black/50 flex flex-col items-center justify-center px-4",
      "transition-opacity duration-300 text-center",
      hovered ? "opacity-100" : "opacity-0"
    )}
    aria-hidden={!hovered}
  >
    <div className="p-4 rounded-lg max-w-[90%]">
      <h3 className="text-2xl md:text-4xl font-noto font-bold text-white mb-1 tracking-wide">
        {card.title}
      </h3>
      {card.subtitle && (
        <p className="text-sm md:text-base text-neutral-200 opacity-90">
          {card.subtitle}
        </p>
      )}
    </div>
  </div>
);

const Card = React.memo(({ card, index, hovered, setHovered, isMobile }: CardProps) => {
  const handleInteraction = () => {
    if (isMobile) {
      setHovered(index === hovered ? null : index);
    }
  };

  return (
    <div
      onMouseEnter={() => !isMobile && setHovered(index)}
      onMouseLeave={() => !isMobile && setHovered(null)}
      onClick={handleInteraction}
      className={cn(
        "relative rounded-lg bg-gray-100 dark:bg-neutral-900 overflow-hidden",
        "h-[35vh] lg:h-[60vh] w-full",
        "transition-all duration-300 ease-out cursor-pointer",
        hovered !== null && hovered !== index && "blur-sm scale-[0.90]"
      )}
      aria-label={`Ver detalles de ${card.title}`}
      role="button"
      tabIndex={0}
    >
      <img
        src={card.src}
        alt={card.title}
        className="object-cover absolute inset-0 w-full h-full"
        loading="lazy"
        decoding="async"
      />
      <CardOverlay hovered={hovered === index} card={card} />
    </div>
  );
});

Card.displayName = "Card";

const animationProps = {
  distance: 300,
  direction: "vertical" as const,
  reverse: false,
  config: { tension: 40, friction: 20 },
  initialOpacity: 0,
  animateOpacity: true,
  scale: 1.1,
  threshold: 0.2,
};

export function FocusCards({ cards }: { cards: CardType[] }) {
  const [hovered, setHovered] = useState<number | null>(null);
  const [isMobile, setIsMobile] = useState(false);

  useEffect(() => {
    const checkMobile = () => setIsMobile(window.innerWidth < 768);
    const handleResize = debounce(checkMobile, 200);
    
    checkMobile();
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  return (
    <div 
      className="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-3 gap-10 max-w-[75%] mx-auto px-4"
      role="region" 
      aria-label="Galería de servicios"
    >
      {cards.map((card, index) => (
        <AnimatedContent 
          key={`${card.title}-${index}`} 
          {...animationProps}
        >
          <Card 
            card={card} 
            index={index} 
            hovered={hovered} 
            setHovered={setHovered} 
            isMobile={isMobile} 
          />
        </AnimatedContent>
      ))}
    </div>
  );
}