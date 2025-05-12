"use client";

import { useRef, useState } from "react";
import { AnimatePresence, motion } from "motion/react";
import { cn } from "../../utils/Utils";

interface DirectionAwareHoverProps {
  imageUrl: string;
  alt?: string;
  children: React.ReactNode | string;
  childrenClassName?: string;
  imageClassName?: string;
  className?: string;
}

export const DirectionAwareHover = ({
  imageUrl,
  alt = "Imagen de fondo",
  children,
  childrenClassName,
  imageClassName,
  className,
}: DirectionAwareHoverProps) => {
  const ref = useRef<HTMLDivElement>(null);
  const [direction, setDirection] = useState<"top" | "bottom" | "left" | "right">("left");

  const handleMouseEnter = (event: React.MouseEvent<HTMLDivElement>) => {
    if (!ref.current) return;
    const dir = calculateDirection(event, ref.current);
    setDirection(dir);
  };

  const calculateDirection = (event: React.MouseEvent<HTMLDivElement>, element: HTMLElement): "top" | "bottom" | "left" | "right" => {
    const { width, height, left, top } = element.getBoundingClientRect();
    const x = event.clientX - left - (width / 2) * (width > height ? height / width : 1);
    const y = event.clientY - top - (height / 2) * (height > width ? width / height : 1);
    const d = Math.round(Math.atan2(y, x) / 1.57079633 + 5) % 4;
    return ["top", "right", "bottom", "left"][d] as "top" | "bottom" | "left" | "right";
  };

  return (
    <motion.div
      onMouseEnter={handleMouseEnter}
      ref={ref}
      className={cn(
        "md:h-96 w-60 h-60 md:w-96 bg-transparent rounded-lg overflow-hidden relative group/card",
        className
      )}
    >
      <AnimatePresence mode="wait">
        <motion.div
          className="relative h-full w-full"
          initial="initial"
          whileHover={direction}
          exit="exit"
        >
          <motion.div className="group-hover/card:block hidden absolute inset-0 w-full h-full z-10 transition duration-500" />

          <motion.div
            variants={variants}
            transition={{ duration: 0.2, ease: "easeOut" }}
            className="h-full w-full relative bg-gray-50"
          >
            <img
              src={imageUrl}
              alt={alt}
              role="img"
              aria-label={alt}
              className={cn("h-full w-full object-cover scale-[1.15]", imageClassName)}
              width="1000"
              height="1000"
            /> 
          </motion.div>

          <motion.div
            variants={textVariants}
            transition={{ duration: 0.5, ease: "easeOut" }}
            className={cn("text-white absolute bottom-4 left-4 z-40", childrenClassName)}
          >
            {children}
          </motion.div>
        </motion.div>
      </AnimatePresence>
    </motion.div>
  );
};

const variants = {
  initial: { x: 0, y: 0 },
  exit: { x: 0, y: 0 },
  top: { y: 20 },
  bottom: { y: -20 },
  left: { x: 20 },
  right: { x: -20 },
};

const textVariants = {
  initial: { y: 0, x: 0, opacity: 0 },
  exit: { y: 0, x: 0, opacity: 0 },
  top: { y: -20, opacity: 1 },
  bottom: { y: 2, opacity: 1 },
  left: { x: -2, opacity: 1 },
  right: { x: 20, opacity: 1 },
};
