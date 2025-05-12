"use client";

import { motion } from "framer-motion";
import { cn } from "../../utils/Utils";

interface CinematicImageProps {
  src: string;
  alt?: string;
  variant: "sloth" | "nature";
  opacity?: number;
}

export default function CinematicImage({
  src,
  alt = "Cinematic visual",
  variant,
  opacity = 0.5,
}: CinematicImageProps) {
  const isSloth = variant === "sloth";

  return (
    <div
      className={cn(
        "absolute top-0 h-full pointer-events-none overflow-hidden z-10",
        isSloth
          ? "left-0 w-[100%] sm:w-[35%] md:w-[100%] lg:w-[50%]"
          : "right-0 w-[0%] sm:w-[35%] md:w-[10%] lg:w-[50%]"
      )}
    >
      <div className="relative w-full h-full">
        <motion.img
          src={src}
          alt={alt}
          className="absolute inset-0 w-full h-full object-cover select-none"
          style={{ opacity }}
          animate={
            isSloth
              ? { scale: [1, 1.03, 1] }
              : { scale: [1, 1.02, 1], rotate: [0, 1, -1, 0] }
          }
          transition={{
            repeat: Infinity,
            duration: 10,
            ease: "easeInOut",
          }}
        />

        {!isSloth && (
          <div className="absolute inset-0 bg-gradient-to-l from-transparent to-[#1a3a17]" />
        )}
      </div>
    </div>
  );
}
