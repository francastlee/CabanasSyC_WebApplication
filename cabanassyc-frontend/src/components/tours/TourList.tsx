import { useEffect, useState } from "react";
import { getTours, Tour } from "../../api/Tours";
import { TourCard } from "./TourCard";
import { useTranslation } from "react-i18next";

export default function TourList() {
  const { t } = useTranslation("tours");
  const [tours, setTours] = useState<Tour[]>([]);

  useEffect(() => {
    getTours().then(setTours).catch(console.error);
  }, []);

  return (
    <section className="w-full min-h-screen bg-[#4B2A1F] py-16 select-none">
      <div className="absolute top-0 left-0 w-full h-40 bg-gradient-to-b from-[#1a3a17] to-transparent pointer-events-none z-10" />
      <h2 className="text-center pt-[80px] md:pt-[100px] md:text-[50px] text-[35px] text-white tracking-in-expand drop-shadow-[0_0_5px_#006817] font-cinzel">
        {t("title")}
      </h2>
      <div className="mx-auto grid grid-cols-2 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-x-6 gap-y-10 mt-10 pb-20 px-4 max-w-[1200px] justify-items-center">
        {tours.map((tour, index) => (
          <TourCard 
            key={tour.id} 
            tour={tour} 
            image={tour.images[0]?.url || "/imgs/home/cabin2.webp"} 
            index={index} 
          />
        ))}
      </div>
      <div className="absolute bottom-0 left-0 w-full h-40 bg-gradient-to-t from-[#1a3a17] to-transparent pointer-events-none z-10" />
    </section>
  );
}
