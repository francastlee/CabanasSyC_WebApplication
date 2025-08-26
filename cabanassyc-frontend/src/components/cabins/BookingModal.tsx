"use client";

import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { AnimatedButton } from "../common/AnimatedButton";
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
  const FaWhatsapp = () => (
    <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="50%" height="50%" viewBox="0,0,256,256">
      <g fill="none" fill-rule="nonzero" stroke="none" stroke-width="1" stroke-linecap="butt" stroke-linejoin="miter" stroke-miterlimit="10" stroke-dasharray="" stroke-dashoffset="0" font-family="none" font-weight="none" font-size="none" text-anchor="none"><g transform="scale(6.4,6.4)"><path d="M4.221,29.298l-0.104,-0.181c-1.608,-2.786 -2.459,-5.969 -2.458,-9.205c0.004,-10.152 8.267,-18.412 18.419,-18.412c4.926,0.002 9.553,1.919 13.03,5.399c3.477,3.48 5.392,8.107 5.392,13.028c-0.005,10.153 -8.268,18.414 -18.42,18.414c-3.082,-0.002 -6.126,-0.776 -8.811,-2.24l-0.174,-0.096l-9.385,2.46z" fill="#ffffff"></path><path d="M20.078,2v0c4.791,0.001 9.293,1.867 12.676,5.253c3.383,3.386 5.246,7.887 5.246,12.674c-0.005,9.878 -8.043,17.914 -17.927,17.914c-2.991,-0.001 -5.952,-0.755 -8.564,-2.18l-0.349,-0.19l-0.384,0.101l-8.354,2.19l2.226,-8.131l0.11,-0.403l-0.208,-0.361c-1.566,-2.711 -2.393,-5.808 -2.391,-8.955c0.004,-9.876 8.043,-17.912 17.919,-17.912M20.078,1c-10.427,0 -18.915,8.485 -18.92,18.912c-0.002,3.333 0.869,6.588 2.525,9.455l-2.683,9.802l10.03,-2.63c2.763,1.507 5.875,2.3 9.042,2.302h0.008c10.427,0 18.915,-8.485 18.92,-18.914c0,-5.054 -1.966,-9.807 -5.538,-13.382c-3.572,-3.574 -8.322,-5.543 -13.384,-5.545z" fill="#788b9c"></path><path d="M19.995,35c-2.504,-0.001 -4.982,-0.632 -7.166,-1.823l-1.433,-0.782l-1.579,0.414l-3.241,0.85l0.83,-3.03l0.453,-1.656l-0.859,-1.488c-1.309,-2.267 -2.001,-4.858 -2,-7.492c0.004,-8.267 6.732,-14.992 14.998,-14.993c4.011,0.001 7.779,1.563 10.61,4.397c2.833,2.834 4.392,6.602 4.392,10.608c-0.004,8.268 -6.732,14.995 -15.005,14.995z" fill="#79ba7e"></path><path d="M28.28,23.688c-0.45,-0.224 -2.66,-1.313 -3.071,-1.462c-0.413,-0.151 -0.712,-0.224 -1.012,0.224c-0.3,0.45 -1.161,1.462 -1.423,1.761c-0.262,0.3 -0.524,0.337 -0.974,0.113c-0.45,-0.224 -1.899,-0.7 -3.615,-2.231c-1.337,-1.191 -2.239,-2.663 -2.501,-3.113c-0.262,-0.45 -0.029,-0.693 0.197,-0.917c0.202,-0.202 0.45,-0.525 0.674,-0.787c0.224,-0.262 0.3,-0.45 0.45,-0.75c0.151,-0.3 0.075,-0.563 -0.038,-0.787c-0.113,-0.224 -1.012,-2.437 -1.387,-3.336c-0.364,-0.876 -0.736,-0.757 -1.012,-0.771c-0.262,-0.014 -0.562,-0.015 -0.861,-0.015c-0.3,0 -0.787,0.113 -1.198,0.563c-0.411,0.45 -1.573,1.537 -1.573,3.749c0,2.212 1.611,4.35 1.835,4.649c0.224,0.3 3.169,4.839 7.68,6.786c1.072,0.462 1.911,0.739 2.562,0.947c1.076,0.342 2.057,0.294 2.832,0.178c0.864,-0.129 2.66,-1.087 3.034,-2.136c0.375,-1.049 0.375,-1.95 0.262,-2.136c-0.111,-0.192 -0.41,-0.305 -0.861,-0.529z" fill="#ffffff"></path></g></g>
    </svg>
  );

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
