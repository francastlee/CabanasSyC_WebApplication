import { useState } from "react";
import { useTranslation } from "react-i18next";
import { FaHiking, FaUtensils, FaPlaneDeparture, FaCheck } from "react-icons/fa";
import { motion, AnimatePresence } from "framer-motion";

type Item = { name: string; distance: string };

export default function InfoSection() {
  const { t } = useTranslation("contact");

  const activities = t("activities.items", { returnObjects: true }) as Item[];

  const restaurants: Item[] = [
    { name: "Kenko", distance: "3.00 km" },
    { name: "Pizzería La Cava", distance: "1.5 km" },
    { name: "Fortuneño", distance: "3.4 km" }
  ];

  const airports: Item[] = [
    { name: t("airports.fortuna"), distance: "11.9 km" },
    { name: t("airports.juanSantaMaria"), distance: "116 km" },
    { name: t("airports.guanacaste"), distance: "143 km" }
  ];

  const [selected, setSelected] = useState<Item | null>(null);
  const [copied, setCopied] = useState(false);

  const openMaps = (item: Item) => {
    const query = encodeURIComponent(`${item.name}, La Fortuna, Costa Rica`);
    window.open(`https://www.google.com/maps/search/?api=1&query=${query}`, "_blank");
  };

  const copyName = async (item: Item) => {
    try {
      await navigator.clipboard.writeText(item.name);
      setCopied(true);
      setTimeout(() => setCopied(false), 1500);
    } catch {}
  };

  return (
    <section className="bg-[#1a3a17] py-16 px-4 select-none">
      <div className="max-w-6xl mx-auto flex flex-col md:flex-row gap-12 md:gap-16 mb-30">
        <InfoColumn
          icon={<FaHiking className="text-amber-400 text-lg" />}
          title={t("activities.title")}
          items={activities}
          onItemClick={setSelected}
        />

        <div className="flex flex-col gap-12 md:gap-10 w-full">
          <InfoColumn
            icon={<FaUtensils className="text-amber-400 text-lg" />}
            title={t("restaurants.title")}
            items={restaurants}
            onItemClick={setSelected}
          />
          <InfoColumn
            icon={<FaPlaneDeparture className="text-amber-400 text-lg" />}
            title={t("airports.title")}
            items={airports}
            onItemClick={setSelected}
          />
        </div>
      </div>

      <AnimatePresence>
        {selected && (
          <motion.div
            key="activity-modal"
            className="fixed inset-0 bg-black/60 z-50 flex items-end sm:items-center justify-center p-4"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
          >
            <motion.div
              className="w-full sm:max-w-md bg-[#243e2b] text-white rounded-2xl p-5 shadow-2xl border border-white/10"
              initial={{ y: 40, scale: 0.98, opacity: 0 }}
              animate={{ y: 0, scale: 1, opacity: 1 }}
              exit={{ y: 40, scale: 0.98, opacity: 0 }}
            >
              <div className="flex items-start justify-between gap-4">
                <div>
                  <h3 className="text-lg font-semibold">{selected.name}</h3>
                  <p className="text-white/70 text-sm">{selected.distance}</p>
                </div>
                <button
                  onClick={() => setSelected(null)}
                  className="text-white/70 hover:text-white text-xl cursor-pointer"
                  aria-label={t("actions.close")}
                >
                  &times;
                </button>
              </div>

              <div className="mt-5 grid grid-cols-1 sm:grid-cols-2 gap-3">
                <button
                  onClick={() => openMaps(selected)}
                  className="w-full px-4 py-3 rounded-xl bg-amber-500 hover:bg-amber-600 text-black font-medium cursor-pointer"
                >
                  {t("actions.viewOnMap")}
                </button>
                <button
                  onClick={() => copyName(selected)}
                  className="w-full px-4 py-3 rounded-xl bg-white/10 hover:bg-white/15 border border-white/15 cursor-pointer"
                >
                  {copied ? t("actions.copied") : t("actions.copyName")}
                </button>
              </div>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </section>
  );
}

function InfoColumn({
  icon,
  title,
  items,
  onItemClick
}: {
  icon: React.ReactNode;
  title: string;
  items: { name: string; distance: string }[];
  onItemClick?: (item: { name: string; distance: string }) => void;
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
            key={`${item.name}-${item.distance}`}
            className="flex justify-between items-center text-white hover:text-amber-300 transition-colors cursor-pointer"
            onClick={() => onItemClick?.(item)}
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
