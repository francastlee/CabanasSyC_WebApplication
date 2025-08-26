import { useState } from "react";
import { Tour } from "../../api/Tours";
import { useTranslation } from "react-i18next";

interface TourCardProps {
  tour: Tour;
  image: string;
  index: number;
}

export const TourCard = ({ tour, image, index }: TourCardProps) => {
  const { t } = useTranslation("tours");
  const [isMobileOpen, setIsMobileOpen] = useState(false);

  const handleCardClick = () => {
    if (window.innerWidth < 768) {
      setIsMobileOpen(!isMobileOpen);
    }
  };

  return (
    <div
      onClick={handleCardClick}
      className="relative group w-[180px] h-[220px] md:w-[250px] md:h-[290px] rounded-[13px] shadow-[0_7px_10px_rgba(0,0,0,1)] overflow-hidden transition-transform duration-500 ease-in-out hover:-translate-y-5 opacity-0 fade-in cursor-pointer"
      style={{ animationDelay: `${index * 100}ms` }}
    >
      <div className="absolute inset-0">
        <img
          src={image}
          alt={tour.name}
          className="w-full h-full object-cover rounded-[13px]"
        />
        <div className={`absolute inset-0 bg-gradient-to-b from-[#4B2A1F] via-[#96705e] to-[#856D5D] transition-opacity duration-500 rounded-[13px] ${isMobileOpen ? "opacity-70" : "opacity-0 group-hover:opacity-70"}`} />
      </div>

      <div className="relative z-10 text-white text-center px-4 h-full w-full">
        <h3
          className={`absolute bottom-4 left-1/2 transform -translate-x-1/2 translate-y-0 transition-transform duration-500 text-md font-bold drop-shadow-[0_0_15px_#4B2A1F] bg-[#4B2A1F]/60 px-2 py-1 rounded-md max-w-[90%] whitespace-nowrap overflow-hidden text-ellipsis
          ${isMobileOpen ? "opacity-0" : "group-hover:translate-y-[-700%]"}`}
        >
          {tour.name}
        </h3>

        <div
          className={`absolute bottom-4 left-1/2 -translate-x-1/2 transition-all duration-500 text-sm space-y-1 pt-12 w-full px-4
          ${isMobileOpen ? "opacity-100 translate-y-0" : "translate-y-4 opacity-0 group-hover:opacity-100 group-hover:translate-y-0"}`}
        >
          <p>👥 {t("card.capacity")}: {tour.capacity} {t("card.people")}</p>
          <p>🕒 {t("card.schedule")}: {tour.startTime} - {tour.endTime}</p>
          <p>💵 {t("card.price")}: ${tour.price} / {t("card.person")}</p>
          <a
            href={`https://api.whatsapp.com/send?phone=+50689859975&text=Me%20interesa%20el%20tour:%20${encodeURIComponent(tour.name)}`}
            target="_blank"
            rel="noopener noreferrer"
          >
            <button className="btn-book bg-[#4B2A1F] hover:bg-[#1a3a17] text-white font-bold py-2 px-4 border-b-4 border-[#1a3a17] rounded cursor-pointer transition-transform duration-300 mt-2">
              {t("card.button")}
            </button>
          </a>
        </div>
      </div>
    </div>
  );
};
