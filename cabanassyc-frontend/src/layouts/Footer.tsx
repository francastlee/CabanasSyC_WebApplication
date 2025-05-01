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

const FooterLocation = ({
  direccion,
  direccionLink,
}: {
  direccion: string;
  direccionLink?: string;
}) => {
  const { t } = useTranslation("footer");
  return (
    <div className="flex items-start gap-4">
      <FaMapMarkerAlt className="text-[#4B2A1F] text-xl mt-1" />
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
  );
};

const FooterContact = ({
  correo,
  telefono,
}: {
  correo?: string;
  telefono?: string;
}) => {
  const { t } = useTranslation("footer");
  return (
    <div className="flex items-start gap-4">
      <FaEnvelope className="text-[#4B2A1F] text-xl mt-1" />
      <div>
        <h4 className="font-semibold">{t("footer.help")}</h4>
        {correo && <p className="text-sm">{correo}</p>}
        {telefono && <p className="text-sm">{telefono}</p>}
      </div>
    </div>
  );
};

const FooterSocial = ({ redes }: { redes: FooterProps["redes"] }) => (
  <div className="flex gap-4">
    {redes?.facebook && (
      <a
        href={redes.facebook}
        target="_blank"
        rel="noopener noreferrer"
        aria-label="Facebook"
        className="hover:text-[#4B2A1F]"
      >
        <FaFacebookF />
      </a>
    )}
    {redes?.booking && (
      <a
        href={redes.booking}
        target="_blank"
        rel="noopener noreferrer"
        aria-label="Booking.com"
        className="hover:text-[#4B2A1F]"
      >
        <TbBrandBooking />
      </a>
    )}
    {redes?.instagram && (
      <a
        href={redes.instagram}
        target="_blank"
        rel="noopener noreferrer"
        aria-label="Instagram"
        className="hover:text-[#4B2A1F]"
      >
        <FaInstagram />
      </a>
    )}
    {redes?.whatsApp && (
      <a
        href={redes.whatsApp}
        target="_blank"
        rel="noopener noreferrer"
        aria-label="WhatsApp"
        className="hover:text-[#4B2A1F]"
      >
        <FaWhatsapp />
      </a>
    )}
  </div>
);

const FooterDeveloper = ({
  desarrollador,
}: {
  desarrollador: FooterProps["desarrollador"];
}) => {
  const { t } = useTranslation("footer");
  if (!desarrollador) return null;
  return (
    <a
      href={desarrollador.url}
      target="_blank"
      rel="noopener noreferrer"
      aria-label="Sitio del desarrollador"
      className="flex items-center gap-2"
    >
      <FaCode className="text-lg" />
      <span className="font-semibold">
        {t("footer.dev")} {desarrollador.nombre}
      </span>
    </a>
  );
};

export default function Footer({
  direccion,
  direccionLink,
  correo,
  telefono,
  redes,
  desarrollador,
}: FooterProps) {
  const { t } = useTranslation("footer");

  return (
    <footer
      role="contentinfo"
      className="bg-[#1a3a17] text-white font-noto select-none"
    >
      <div className="max-w-7xl mx-auto py-12 px-6 grid grid-cols-1 md:grid-cols-3 gap-10">
        <div>
          <h3 className="text-xl font-semibold mb-3">{t("footer.comment")}</h3>
          <p className="text-sm leading-relaxed tracking-wide">
            {t("footer.commentDetail")}
          </p>
        </div>

        {direccion && (
          <FooterLocation
            direccion={direccion}
            direccionLink={direccionLink}
          />
        )}
        {(correo || telefono) && (
          <FooterContact correo={correo} telefono={telefono} />
        )}
      </div>

      <div className="border-t border-[#4B2A1F] mt-6 py-6 px-6 flex flex-col md:flex-row justify-between items-center gap-4">
        <FooterDeveloper desarrollador={desarrollador} />
        <p className="text-sm text-center md:text-left">
          © {new Date().getFullYear()} Cabañas SyC | {t("footer.rights")}
        </p>
        <FooterSocial redes={redes} />
      </div>
    </footer>
  );
}
