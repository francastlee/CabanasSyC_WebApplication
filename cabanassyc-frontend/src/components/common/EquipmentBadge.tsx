import { JSX } from "react/jsx-runtime";

interface Props {
  label: string;
}

const svgIcons: Record<string, JSX.Element> = {
  "Double bed": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M4 7h16v2H4V7zm0 4h16v6h2v2h-2v2h-2v-2H6v2H4v-2H2v-2h2v-6zm2 2v2h12v-2H6z"/></svg>
  ),
  "Doble bed": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M4 7h16v2H4V7zm0 4h16v6h2v2h-2v2h-2v-2H6v2H4v-2H2v-2h2v-6zm2 2v2h12v-2H6z"/></svg>
  ),
  "Single bed": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M3 7h12v2H3V7zm0 4h12v4h2v2h-2v2h-2v-2H5v2H3v-2H1v-2h2v-4zm2 2v2h8v-2H5z"/></svg>
  ),
  "Couch": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M3 13v-2a5 5 0 0110 0v2h1v-2a5 5 0 0110 0v2h-2v4H5v-4H3z"/></svg>
  ),
  "Smart TV": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M21 3H3c-1.1 0-1.99.9-1.99 2L1 17h6v2H5v2h14v-2h-2v-2h6V5c0-1.1-.9-2-2-2z"/></svg>
  ),
  "WiFi": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M12 20a2 2 0 110-4 2 2 0 010 4zm6-3a8 8 0 00-12 0l1.5 1.5a6 6 0 019 0L18 17zm3-3a12 12 0 00-18 0l1.5 1.5a10 10 0 0115 0L21 14z"/></svg>
  ),
  "Fan": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M12 12a3 3 0 100-6 3 3 0 000 6zM2 12a10 10 0 0110-10v4a6 6 0 000 12v4a10 10 0 01-10-10zm20 0a10 10 0 01-10 10v-4a6 6 0 000-12V2a10 10 0 0110 10z"/></svg>
  ),
  "Extinguisher": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M12 2l4 4-4 4-4-4 4-4zm-2 6h4v14h-4V8z"/></svg>
  ),
  "Kitchen": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M3 3h18v2H3V3zm0 4h18v2H3V7zm0 4h18v2H3v-2zm0 4h18v2H3v-2zm0 4h18v2H3v-2z"/></svg>
  ),
  "Microwave": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M3 5h18v14H3V5zm2 2v10h14V7H5zm2 2h10v6H7V9z"/></svg>
  ),
  "Fridge": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M7 2h10v20H7V2zm2 4v2h2V6H9z"/></svg>
  ),
  "BBQ": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M12 2a5 5 0 015 5h-2a3 3 0 00-6 0H7a5 5 0 015-5zm-1 8h2v4h-2v-4zm-6 6h14v2H5v-2z"/></svg>
  ),
  "Shower": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M17 5V3H7v2H4v2h16V5h-3zM6 20a2 2 0 114 0H6zm8 0a2 2 0 114 0h-4z"/></svg>
  ),
  "Toilet": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M6 3h12v3H6V3zm2 4h8v3H8V7zm0 5h8v7H8v-7zM5 21v-2h14v2H5z"/></svg>
  ),
  "Towel": (
    <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24"><path d="M7 2h10a3 3 0 013 3v14a3 3 0 01-3 3H7a3 3 0 01-3-3V5a3 3 0 013-3zm0 2a1 1 0 00-1 1v14a1 1 0 001 1h10a1 1 0 001-1V5a1 1 0 00-1-1H7z"/></svg>
  ),
};

export const EquipmentBadge = ({ label }: Props) => {
  const icon = svgIcons[label];

  return (
    <span className="bg-[#1a3a17]/60 text-white text-xs font-semibold px-3 py-1 rounded-full flex items-center gap-2">
      {icon || "🔧"}
      <span className="hidden sm:inline">{label}</span>
    </span>
  );
};
