import { useTranslation } from "react-i18next";
import { FaHiking, FaUtensils, FaPlaneDeparture, FaCheck } from "react-icons/fa";

export default function InfoSection() {
  const { t } = useTranslation("contact");

  const activities = [
    { name: "Parque Ecoglide", distance: "2.3 km" },
    { name: "Parque central Fortuna", distance: "2.3 km" },
    { name: "Parque Nacional Volcán Arenal", distance: "2.3 km" },
    { name: "Parque Arenal Natural Ecológico", distance: "2.3 km" },
    { name: "Parque Sky Adventures", distance: "2.3 km" },
    { name: "Zoológico Arenal", distance: "2.3 km" },
    { name: "Parque Mistico", distance: "2.3 km" },
    { name: "Los Mejengueros", distance: "2.3 km" },
    { name: "Parque Selvatura", distance: "2.3 km" },
  ];

  const restaurants = [
    { name: "Kenko", distance: "1.2 km" },
    { name: "Pizza Cava", distance: "1.5 km" },
    { name: "Fortuneño", distance: "2.0 km" },
  ];

  const airports = [
    { name: t("airports.fortuna"), distance: "10 km" },
    { name: t("airports.juanSantaMaria"), distance: "120 km" },
    { name: t("airports.sanCristobal"), distance: "75 km" },
  ];

  return (
    <section className="bg-[#1a3a17] py-16 px-4 select-none">
      <div className="max-w-6xl mx-auto flex flex-col md:flex-row gap-12 md:gap-16 mb-30">
        <InfoColumn
          icon={<FaHiking className="text-amber-400 text-lg" />}
          title={t("activities.title")}
          items={activities}
        />

        <div className="flex flex-col gap-12 md:gap-10 w-full">
          <InfoColumn
            icon={<FaUtensils className="text-amber-400 text-lg" />}
            title={t("restaurants.title")}
            items={restaurants}
          />
          <InfoColumn
            icon={<FaPlaneDeparture className="text-amber-400 text-lg" />}
            title={t("airports.title")}
            items={airports}
          />
        </div>
      </div>
    </section>
  );
}

function InfoColumn({
  icon,
  title,
  items,
}: {
  icon: React.ReactNode;
  title: string;
  items: { name: string; distance: string }[];
}) {
  return (
    <div className="w-full">
      <div className="flex items-center mb-4">
        {icon}
        <h2 className="text-lg md:text-xl font-semibold text-white ml-2">{title}</h2>
      </div>
      <ul className="space-y-2 border-l border-amber-400 pl-4">
        {items.map((item) => (
          <li
            key={item.name}
            className="flex justify-between items-center text-white hover:text-amber-300 transition-colors cursor-pointer"
          >
            <div className="flex items-center gap-2">
              <FaCheck className="text-amber-400" />
              <span className="text-sm md:text-base">{item.name}</span>
            </div>
            <span className="text-xs md:text-sm text-white/70 ml-3">{item.distance}</span>
          </li>
        ))}
      </ul>
    </div>
  );
}
