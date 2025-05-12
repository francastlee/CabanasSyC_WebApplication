"use client";
import { FC, useEffect, useState } from "react";
import { EquipmentBadge } from "../common/EquipmentBadge";
import { Cabin } from "../../api/Cabins";
import { useTranslation } from "react-i18next";
import { FaArrowLeft, FaArrowRight, FaStar } from "react-icons/fa";

interface Props {
  cabin: Cabin;
  onReserveClick: () => void;
}

export const CabinCard: FC<Props> = ({ cabin, onReserveClick }) => {
  const { t } = useTranslation("cabins");
  const [activeIndex, setActiveIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setActiveIndex((prev) =>
        prev === cabin.cabinImages.length - 1 ? 0 : prev + 1
      );
    }, 5000); 

    return () => clearInterval(interval); 
  }, [cabin.cabinImages.length]);

  const nextImage = () =>
    setActiveIndex((prev) =>
      prev === cabin.cabinImages.length - 1 ? 0 : prev + 1
    );

  const prevImage = () =>
    setActiveIndex((prev) =>
      prev === 0 ? cabin.cabinImages.length - 1 : prev - 1
    );

  return (
    <div className="w-full max-w-[26rem] min-h-[100%] bg-[#4B2A1F] rounded-xl shadow-xl overflow-hidden font-karla text-white relative">
      <div className="relative h-56 overflow-hidden">
        {cabin.cabinImages.map((img, index) => (
          <img
            key={img.id}
            src={img.url}
            alt={cabin.name}
            className={`absolute inset-0 w-full h-full object-contain transition-opacity duration-700 ${
              index === activeIndex ? "opacity-100" : "opacity-0"
            }`}
          />
        ))}
        <div className="absolute inset-0 bg-gradient-to-tr from-transparent via-transparent to-black/60" />

        <button
          onClick={prevImage}
          className="absolute left-2 top-1/2 -translate-y-1/2 bg-black/40 p-1 rounded-full hover:bg-black/60"
        >
          <FaArrowLeft />
        </button>
        <button
          onClick={nextImage}
          className="absolute right-2 top-1/2 -translate-y-1/2 bg-black/40 p-1 rounded-full hover:bg-black/60"
        >
          <FaArrowRight />
        </button>

        <div className="absolute bottom-2 left-1/2 -translate-x-1/2 flex gap-1 z-10">
          {cabin.cabinImages.map((_, i) => (
            <span
              key={i}
              className={`h-2 w-2 rounded-full cursor-pointer ${
                i === activeIndex ? "bg-white" : "bg-white/50"
              }`}
              onClick={() => setActiveIndex(i)}
            />
          ))}
        </div>
      </div>

      <div className="p-6">
        <div className="flex items-center justify-between mb-3">
          <h3 className="text-xl font-bold font-cinzel">{cabin.name}</h3>
          <div className="flex items-center text-yellow-500 text-sm gap-1">
            <FaStar className="text-base" /> 5.0
          </div>
        </div>

        <p className="text-sm mb-2">
          {t("cabinCard.type")}: {cabin.cabinType.name} ·{" "}
          {t("cabinCard.capacity")}: {cabin.cabinType.capacity}{" "}
          {t("cabinCard.people")}
        </p>

        <p className="text-yellow-500 font-bold mb-4">
          ${cabin.cabinType.price} / {t("cabinCard.night")}
        </p>

        <div className="overflow-hidden w-full">
          <div className="flex animate-scroll-x gap-2">
            {[...cabin.equipments, ...cabin.equipments].map((eq, i) => (
              <EquipmentBadge key={i} label={eq.name} />
            ))}
          </div>
        </div>

        <button
          onClick={onReserveClick}
          className="w-full bg-[#1a3a17] hover:bg-green-800 text-white py-2 px-4 rounded-lg transition cursor-pointer mt-2"
        >
          {t("cabinCard.bookNow")}
        </button>
      </div>
    </div>
  );
};
