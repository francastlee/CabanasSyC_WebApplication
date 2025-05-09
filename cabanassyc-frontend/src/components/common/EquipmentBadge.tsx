import { FaBed, FaTv, FaWifi, FaFan, FaFireExtinguisher, FaCouch, FaShower } from "react-icons/fa";
import { MdKitchen, MdOutlineMicrowave } from "react-icons/md";
import { GiBarbecue, GiTowel } from "react-icons/gi";
import { RiFridgeFill } from "react-icons/ri";
import { PiToiletBold } from "react-icons/pi";
import { JSX } from "react";

interface Props {
  label: string;
}

const equipmentIcons: Record<string, JSX.Element> = {
  "Double bed": <FaBed />,
  "Doble bed": <FaBed />,
  "Single bed": <FaBed />,
  "Couch": <FaCouch />,
  "Smart TV": <FaTv />,
  "WiFi": <FaWifi />,
  "Fan": <FaFan />,
  "Extinguisher": <FaFireExtinguisher />,
  "Kitchen": <MdKitchen />,
  "Microwave": <MdOutlineMicrowave />,
  "Fridge": <RiFridgeFill />,
  "BBQ": <GiBarbecue />,
  "Shower": <FaShower />,
  "Toilet": <PiToiletBold />,
  "Towel": <GiTowel />,
};

export const EquipmentBadge = ({ label }: Props) => {
  const icon = equipmentIcons[label];

  return (
    <span className="bg-[#1a3a17]/60 text-white text-xs font-semibold px-3 py-1 rounded-full flex items-center gap-2">
      {icon || "🔧"}
      <span className="hidden sm:inline">{label}</span> 
    </span>
  );
};
