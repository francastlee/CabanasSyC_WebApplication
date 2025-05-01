"use client";

import React, { useState, useEffect } from "react";
import { cn } from "../../utils/Utils";
import AnimatedContent from "./AnimatedContent";

type CardType = {
  title: string;
  subtitle?: string;
  src: string;
};

export const Card = React.memo(
  ({
    card,
    index,
    hovered,
    setHovered,
    isMobile,
  }: {
    card: CardType;
    index: number;
    hovered: number | null;
    setHovered: React.Dispatch<React.SetStateAction<number | null>>;
    isMobile: boolean;
  }) => (
    <div
      onMouseEnter={() => !isMobile && setHovered(index)}
      onMouseLeave={() => !isMobile && setHovered(null)}
      onClick={() => isMobile && setHovered(index === hovered ? null : index)}
      className={cn(
        "relative rounded-lg bg-gray-100 dark:bg-neutral-900 overflow-hidden h-[25vh] md:h-[30vh] lg:h-[80vh] w-full transition-all duration-300 ease-out cursor-pointer",
        hovered !== null && hovered !== index && "blur-sm scale-[0.90]"
      )}
    >
      <img
        src={card.src}
        alt={card.title}
        className="object-cover absolute inset-0 w-full h-full"
      />
      <div
          className={cn(
            "absolute inset-0 bg-black/50 flex flex-col items-center justify-center px-4 transition-opacity duration-300 text-center",
            hovered === index ? "opacity-100" : "opacity-0"
          )}
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
    </div>
  )
);

Card.displayName = "Card";

export function FocusCards({ cards }: { cards: CardType[] }) {
  const [hovered, setHovered] = useState<number | null>(null);
  const [isMobile, setIsMobile] = useState<boolean>(false);

  useEffect(() => {
    const checkMobile = () => setIsMobile(window.innerWidth < 768);
    checkMobile();
    window.addEventListener("resize", checkMobile);
    return () => window.removeEventListener("resize", checkMobile);
  }, []);

  return (
    <div className="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-3 gap-10 max-w-[60%] mx-auto px-4">
      {cards.map((card, index) => (
        <AnimatedContent
          key={card.title}
          distance={300}
          direction="vertical"
          reverse={false}
          config={{ tension: 40, friction: 20 }}
          initialOpacity={0}
          animateOpacity
          scale={1.1}
          threshold={0.2}
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
