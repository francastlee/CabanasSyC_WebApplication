import { ReactNode } from "react";
import { cn } from "../../utils/Utils";

interface AnimatedButtonProps {
  children: ReactNode;
  icon?: ReactNode;
  className?: string;
  onClick?: () => void;
}

export const AnimatedButton = ({
  children,
  icon,
  className,
  onClick,
}: AnimatedButtonProps) => {
  return (
    <button
      onClick={onClick}
      className={cn(
        "relative overflow-hidden px-6 py-3 bg-[#4B2A1F] text-white rounded-full font-medium group transition duration-300 ease-in-out cursor-pointer text-glow-green font-noto",
        className
      )}
    >
      <span className="block transform transition-transform duration-300 group-hover:translate-x-40 ">
        {children}
      </span>
      <span className="absolute inset-0 flex items-center justify-center translate-x-[-100%] group-hover:translate-x-0 transition-transform duration-300 ">
        {icon ?? "🚀"}
      </span>
    </button>
  );
};
