"use client";

import {
  FaMapMarkerAlt,
  FaEnvelope,
  FaFacebookF,
  FaInstagram,
  FaCode,
  FaWhatsapp,
} from "react-icons/fa";
import { TbBrandBooking } from "react-icons/tb";
import { useTranslation } from "react-i18next";

type FooterProps = {
  direccion?: string;
  direccionLink?: string;
  correo?: string;
  telefono?: string;
  color?: string;
  redes?: {
    facebook?: string;
    booking?: string;
    instagram?: string;
    whatsApp?: string;
  };
  desarrollador?: {
    nombre: string;
    url: string;
  };
};

const determineColorPair = (bgColor: string) => {
  return bgColor === "#4B2A1F"
    ? { border: "#1a3a17", icon: "#1a3a17" }
    : { border: "#4B2A1F", icon: "#4B2A1F" };
};

export default function Footer({
  direccion,
  direccionLink,
  correo,
  telefono,
  redes,
  desarrollador,
  color = "#1a3a17",
}: FooterProps) {
  const { t } = useTranslation("footer");
  const { border, icon } = determineColorPair(color);

  return (
    <footer
      role="contentinfo"
      className="text-white font-noto select-none"
      style={{ backgroundColor: color }}
    >
      <div className="max-w-7xl mx-auto py-12 px-6 grid grid-cols-1 md:grid-cols-3 gap-10">
        <div>
          <h3 className="text-xl font-semibold mb-3">{t("footer.comment")}</h3>
          <p className="text-sm leading-relaxed tracking-wide">
            {t("footer.commentDetail")}
          </p>
        </div>

        {direccion && (
          <div className="flex items-start gap-4">
            <FaMapMarkerAlt style={{ color: icon }} className="text-xl mt-1" />
            <a
              href={direccionLink || "#"}
              target="_blank"
              rel="noopener noreferrer"
              aria-label={t("footer.location")}
              className="hover:underline"
            >
              <h4 className="font-semibold">{t("footer.location")}</h4>
              <p className="text-sm">{direccion}</p>
            </a>
          </div>
        )}

        {(correo || telefono) && (
          <div className="flex items-start gap-4">
            <FaEnvelope style={{ color: icon }} className="text-xl mt-1" />
            <div>
              <h4 className="font-semibold">{t("footer.help")}</h4>
              {correo && <p className="text-sm">{correo}</p>}
              {telefono && <p className="text-sm">{telefono}</p>}
            </div>
          </div>
        )}
      </div>

      <div
        className="mt-6 py-6 px-6 flex flex-col md:flex-row justify-between items-center gap-4"
        style={{ borderTop: `1px solid ${border}` }}
      >
        {desarrollador && (
          <a
            href={desarrollador.url}
            target="_blank"
            rel="noopener noreferrer"
            className="flex items-center gap-2"
            aria-label="Sitio del desarrollador"
          >
            <FaCode style={{ color: icon }} className="text-lg" />
            <span className="font-semibold">
              {t("footer.dev")} {desarrollador.nombre}
            </span>
          </a>
        )}

        <p className="text-sm text-center md:text-left">
          © {new Date().getFullYear()} Cabañas SyC | {t("footer.rights")}
        </p>

        <div className="flex gap-4">
          {redes?.facebook && (
            <a
              href={redes.facebook}
              target="_blank"
              rel="noopener noreferrer"
              aria-label="Facebook"
            >
              <FaFacebookF style={{ color: icon }} />
            </a>
          )}
          {redes?.booking && (
            <a
              href={redes.booking}
              target="_blank"
              rel="noopener noreferrer"
              aria-label="Booking.com"
            >
              <TbBrandBooking style={{ color: icon }} />
            </a>
          )}
          {redes?.instagram && (
            <a
              href={redes.instagram}
              target="_blank"
              rel="noopener noreferrer"
              aria-label="Instagram"
            >
              <FaInstagram style={{ color: icon }} />
            </a>
          )}
          {redes?.whatsApp && (
            <a
              href={redes.whatsApp}
              target="_blank"
              rel="noopener noreferrer"
              aria-label="WhatsApp"
            >
              <FaWhatsapp style={{ color: icon }} />
            </a>
          )}
        </div>
      </div>
    </footer>
  );
}
