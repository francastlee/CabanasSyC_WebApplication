"use client";
import { useEffect, useState } from "react";
import { CabinCard } from "./CabinCard";
import { getCabinsWithDetails } from "../../api/Cabins";
import { Cabin } from "../../api/Cabins";
import { useTranslation } from "react-i18next";
import BookingModal from "./BookingModal";

export default function CabinList() {
  const [cabins, setCabins] = useState<Cabin[]>([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedCabin, setSelectedCabin] = useState<Cabin | null>(null);
  const { t } = useTranslation("cabins");

  useEffect(() => {
    getCabinsWithDetails().then(setCabins).catch(console.error);
  }, []);

  const openModal = (cabin: Cabin) => {
    setSelectedCabin(cabin);
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
    setSelectedCabin(null);
  };

  return (
    <section className="relative py-16 px-4 bg-[#1a3a17] min-h-screen select-none overflow-hidden">
      <div className="absolute top-0 left-0 w-full h-40 bg-gradient-to-b from-[#4B2A1F] to-transparent pointer-events-none z-10" />

      <img
        src="imgs/cabins/colibri.png"
        className="absolute w-1/2 sm:w-[30%] md:w-[28%] top-20 left-0 colibri-mask colibri-animation z-10"
        alt="Colibrí"
      />
      <img
        src="imgs/cabins/tucan.png"
        className="absolute w-60 sm:w-[30%] md:w-[28%] top-20 lg:top-auto lg:bottom-5 right-0 tucan-mask tucan-animation z-10"
        alt="Tucán"
      />


      <h2 className="text-white text-4xl sm:text-5xl md:text-6xl font-cinzel text-center mt-20 text-glow-brown z-20 relative">
        {t("cabins.title")}
      </h2>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-x-8 gap-y-12 px-4 mx-auto max-w-screen-xl mt-16 z-20 relative">
        {cabins.map((cabin, index) => (
          <div
            key={cabin.id}
            className="opacity-0 animate-fade-up animation-fill-forwards"
            style={{ animationDelay: `${index * 400}ms` }}
          >
            <CabinCard cabin={cabin} onReserveClick={() => openModal(cabin)} />
          </div>
        ))}
      </div>

      {selectedCabin && (
        <BookingModal
          open={modalOpen}
          onClose={closeModal}
          cabinName={selectedCabin.name}
          capacity={selectedCabin.cabinType.capacity}
        />
      )}

      <div className="absolute bottom-0 left-0 w-full h-40 bg-gradient-to-t from-[#4B2A1F] to-transparent pointer-events-none z-10" />
    </section>
  );
}
