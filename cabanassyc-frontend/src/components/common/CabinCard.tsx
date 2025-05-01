"use client";

import { FC } from "react";
import { DirectionAwareHover } from "../animations/DirectionAwareHover";
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
  alt = "Imagen de la cabaña",
}) => {
  const { t } = useTranslation();

  return (
    <DirectionAwareHover
      imageUrl={imageUrl}
      alt={alt}
      className="group w-[90vw] max-w-xs sm:max-w-sm md:max-w-md lg:max-w-lg xl:max-w-xl 2xl:max-w-2xl rounded-2xl shadow-lg transition-transform duration-300 hover:scale-[1.02] cursor-pointer"
    >
      <article className="mx-auto flex flex-col justify-center items-center text-center bg-black/40 px-4 py-6 rounded-xl backdrop-blur-sm w-60 md:w-80 font-noto">
        <h3 className="text-base sm:text-lg md:text-xl font-bold text-white leading-tight">
          {t(title)}
        </h3>
        <p className="text-sm sm:text-base text-white/80 mt-1 leading-snug">
          {t(description)}
        </p>
      </article>
    </DirectionAwareHover>

  );
};
