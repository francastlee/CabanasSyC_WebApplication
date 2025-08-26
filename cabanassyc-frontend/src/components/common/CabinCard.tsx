"use client";

import { FC, useRef, useState } from "react";
import { useTranslation } from "react-i18next";

interface CabinCardProps {
  title: string;
  description: string;
  imageUrl: string;
  alt?: string;
}

export const CabinCard: FC<CabinCardProps> = ({
  title = "Sin título",
  description = "Descripción no disponible",
  imageUrl,
}) => {
  const { t } = useTranslation();
  const cardRef = useRef<HTMLDivElement>(null);
  const [maskStyle, setMaskStyle] = useState({});

  const handleMouseMove = (e: React.MouseEvent) => {
    const rect = cardRef.current?.getBoundingClientRect();
    if (!rect) return;

    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    const mask = `radial-gradient(circle 200px at ${x}px ${y}px, transparent 0%, rgba(0,0,0,0.5) 60%, rgba(0,0,0,0.9) 100%)`;

    setMaskStyle({
      WebkitMaskImage: mask,
      maskImage: mask,
      WebkitMaskRepeat: "no-repeat",
      maskRepeat: "no-repeat",
      WebkitMaskSize: "cover",
      maskSize: "cover",
      transition: "mask-image 0.4s ease, -webkit-mask-image 0.4s ease",
    });
  };

  const handleMouseLeave = () => {
    setMaskStyle({});
  };

  return (
    <div
      ref={cardRef}
      onMouseMove={handleMouseMove}
      onMouseLeave={handleMouseLeave}
      className="relative w-100 h-80 rounded-xl overflow-hidden shadow-xl shrink-0 transition-all duration-300 cursor-pointer"
      style={{
        backgroundImage: `url(${imageUrl})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
        ...maskStyle,
      }}
    >
      <div className="absolute inset-0 z-10 flex flex-col justify-center items-center text-center p-4 text-white">
        <h3 className="text-xl font-bold">{t(title)}</h3>
        <p className="text-sm mt-2 text-white/80">{t(description)}</p>
      </div>
    </div>
  );
};
