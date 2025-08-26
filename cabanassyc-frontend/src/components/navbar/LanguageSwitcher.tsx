"use client";

import { useTranslation } from "react-i18next";
import { useEffect, useRef, useState } from "react";



const LanguageSwitcher = () => {
  const { i18n } = useTranslation();
  const [open, setOpen] = useState(false);
  const ref = useRef<HTMLDivElement>(null);
  const changeLanguage = (code: string) => {
    i18n.changeLanguage(code);
    setOpen(false);
  };
  const { t } = useTranslation('navbar');

  const languages = [
    { code: "es", label: t('client.language.spanish'), flag: "🇪🇸" },
    { code: "en", label: t('client.language.english'), flag: "🇬🇧" },
  ];

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (ref.current && !ref.current.contains(event.target as Node)) {
        setOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <div ref={ref} className="relative text-white font-karla">
      <button
        onClick={() => setOpen(!open)}
        className="bg-[#4B2A1F] hover:bg-[#856D5D] transition px-4 py-2 rounded-full text-sm shadow flex items-center gap-2 cursor-pointer"
      >
        🌐 {i18n.language.toUpperCase()}
        <svg className="w-3 h-3 ml-1" fill="white" viewBox="0 0 20 20">
          <path d="M10 12l-6-6h12l-6 6z" />
        </svg>
      </button>

      {open && (
        <div className="absolute mt-5 bg-[#4B2A1F] rounded-md shadow-lg z-20 w-40 text-sm">
          {languages.map((lang) => (
            <button
              key={lang.code}
              onClick={() => changeLanguage(lang.code)}
              className={`block w-full px-4 py-2 text-left hover:bg-[#856D5D] cursor-pointer ${
                i18n.language === lang.code ? "font-bold text-yellow-400" : ""
              }`}
            >
              {lang.flag} {lang.label}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

export default LanguageSwitcher;