"use client";

import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { AnimatedButton } from "../common/AnimatedButton";
import { FaWhatsapp } from "react-icons/fa";
import { useTranslation } from "react-i18next";

interface BookingModalProps {
  open: boolean;
  onClose: () => void;
  cabinName: string;
  capacity: number;
}

export default function BookingModal({ open, onClose, cabinName, capacity }: BookingModalProps) {
  const { user } = useContext(AuthContext);
  const [name, setName] = useState("");
  const [checkIn, setCheckIn] = useState("");
  const [checkOut, setCheckOut] = useState("");
  const [adults, setAdults] = useState("");
  const [children, setChildren] = useState("");
  const [error, setError] = useState("");

  const { t } = useTranslation("cabins");

  useEffect(() => {
    if (user?.name) setName(user.name);
  }, [user]);

  if (!open) return null;

  const isTodayOrFuture = (dateStr: string) => {
    const todayStr = new Date().toISOString().split("T")[0];
    return dateStr >= todayStr;
  };

  const isAfter = (dateStr1: string, dateStr2: string) =>
    new Date(dateStr1) > new Date(dateStr2);

  const handleReserve = () => {
    setError("");

    const totalPeople = Number(adults) + Number(children);

    if (!name || !checkIn || !checkOut || !adults) {
      return setError(t("errors.required"));
    }

    if (!isTodayOrFuture(checkIn)) {
      return setError(t("errors.futureDate"));
    }

    if (!isAfter(checkOut, checkIn)) {
      return setError(t("errors.dateOrder"));
    }

    if (Number(adults) < 1) {
      return setError(t("errors.adultMin"));
    }

    if (totalPeople > capacity) {
      return setError(t("errors.limit", { capacity }));
    }

    const message = t("messages.message", {
      cabin: cabinName,
      name,
      checkIn,
      checkOut,
      adults,
      children
    });

    const whatsappUrl = `https://wa.me/50689859975?text=${encodeURIComponent(message)}`;
    window.open(whatsappUrl, "_blank");
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black/50 z-50 flex items-center justify-center px-4">
      <div className="bg-[#4B2A1F] text-white rounded-2xl shadow-2xl w-full max-w-lg p-8 relative animate-fade-in">
        <button
          className="absolute top-3 right-5 text-3xl font-bold text-white hover:text-gray-300"
          onClick={onClose}
        >
          ×
        </button>

        <h2 className="text-2xl font-cinzel mb-6 text-center">{t("modal.title", { cabin: cabinName })}</h2>

        {error && (
          <p className="bg-red-500/20 border border-red-500 text-red-200 font-semibold text-sm p-2 rounded mb-4">
            {error}
          </p>
        )}

        <div className="space-y-4">
          {!user && (
            <div>
              <label className="text-sm font-semibold block mb-1">{t("modal.fullName")}</label>
              <input
                type="text"
                className="w-full rounded-lg px-4 py-2 bg-white/10 border border-white/20 focus:outline-none focus:ring-2 focus:ring-green-300 text-white"
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder={t("modal.placeholder")}
              />
            </div>
          )}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <label className="text-sm font-semibold block mb-1">{t("modal.checkIn")}</label>
              <input
                type="date"
                className="w-full rounded-lg px-4 py-2 bg-white/10 border border-white/20 text-white"
                value={checkIn}
                onChange={(e) => setCheckIn(e.target.value)}
              />
            </div>
            <div>
              <label className="text-sm font-semibold block mb-1">{t("modal.checkOut")}</label>
              <input
                type="date"
                className="w-full rounded-lg px-4 py-2 bg-white/10 border border-white/20 text-white"
                value={checkOut}
                onChange={(e) => setCheckOut(e.target.value)}
              />
            </div>
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <label className="text-sm font-semibold block mb-1">{t("modal.adults")}</label>
              <input
                type="number"
                min="1"
                className="w-full rounded-lg px-4 py-2 bg-white/10 border border-white/20 text-white"
                value={adults}
                onChange={(e) => setAdults(e.target.value)}
              />
            </div>
            <div>
              <label className="text-sm font-semibold block mb-1">{t("modal.children")}</label>
              <input
                type="number"
                min="0"
                className="w-full rounded-lg px-4 py-2 bg-white/10 border border-white/20 text-white"
                value={children}
                onChange={(e) => setChildren(e.target.value)}
              />
            </div>
          </div>

          <AnimatedButton
            icon={<FaWhatsapp />}
            className="w-full bg-[#1a3a17] hover:bg-green-800 text-white py-3 rounded-lg font-semibold text-lg mt-2 transition"
            onClick={handleReserve}
          >
            {t("modal.btn")}
          </AnimatedButton>
        </div>
      </div>
    </div>
  );
}
