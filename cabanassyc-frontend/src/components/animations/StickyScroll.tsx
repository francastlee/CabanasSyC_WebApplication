"use client";
import { useEffect, useState } from "react";
import { motion } from "framer-motion";
import { cn } from "../../utils/Utils";

export const StickyScroll = ({
  content,
  contentClassName,
}: {
  content: {
    title: string;
    description: string;
    imageUrl?: string;
  }[];
  contentClassName?: string;
}) => {
  const [activeCard, setActiveCard] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setActiveCard((prev) => (prev + 1) % content.length);
    }, 6000);
    return () => clearInterval(interval);
  }, [content.length]);

  const backgroundColors = ["#1a3a17"];
  const textColors = ["#94a3b8", "#38bdf8", "#f472b6"];

  return (
    <motion.div
      animate={{ backgroundColor: backgroundColors[0] }}
      className="relative flex flex-col lg:flex-row min-h-screen lg:h-screen justify-center items-center space-y-6 lg:space-y-0 lg:space-x-10 rounded-md px-4 sm:px-6 md:px-10 py-6 sm:py-10 overflow-hidden"
    >
      <img
        src="/imgs/cabins/colibri.png"
        alt="Decorative Colibri"
        className="absolute top-10 left-0 w-80 opacity-50 animate-pulse pointer-events-none"
      />

      <div className="relative flex flex-col justify-start items-center px-4 w-full max-w-2xl">
        {content.map((item, index) => (
          <div
            key={item.title + index}
            className={cn(
              "absolute text-center transition-opacity duration-700 ease-in-out",
              activeCard === index ? "opacity-100" : "opacity-0"
            )}
          >
            <motion.h2
              initial={{ opacity: 0, y: 20 }}
              animate={{
                opacity: activeCard === index ? 1 : 0,
                y: activeCard === index ? 0 : 20,
              }}
              transition={{ duration: 0.6 }}
              style={{
                color:
                  activeCard === index
                    ? textColors[index % textColors.length]
                    : "#94a3b8",
              }}
              className="text-3xl font-bold font-cinzel drop-shadow-md"
            >
              {item.title}
            </motion.h2>

            <motion.p
              initial={{ opacity: 0 }}
              animate={{ opacity: activeCard === index ? 1 : 0 }}
              transition={{ duration: 0.6, delay: 0.2 }}
              className="mt-3 max-w-md mx-auto text-slate-300 text-base"
            >
              {item.description}
            </motion.p>

            <div className="flex justify-center mt-5 space-x-2">
              {content.map((_, i) => (
                <div
                  key={i}
                  className={`w-2 h-2 rounded-full transition duration-300 ${
                    i === activeCard ? "bg-white scale-125" : "bg-white/30"
                  }`}
                />
              ))}
            </div>
          </div>
        ))}
      </div>

      <div
        className={cn(
          "relative w-full max-w-md h-[250px] sm:h-[300px] md:h-[350px] overflow-hidden rounded-md bg-white shadow-lg mt-4 lg:mt-0",
          contentClassName
        )}
      >
        {content[activeCard].imageUrl && (
          <>
            <motion.img
              key={content[activeCard].imageUrl}
              src={content[activeCard].imageUrl}
              alt={content[activeCard].title}
              className="h-full w-full object-cover"
              initial={{ opacity: 0, scale: 1.05 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ duration: 0.6 }}
            />
            <div className="absolute inset-0 bg-gradient-to-t from-black/40 to-transparent" />
          </>
        )}
      </div>
    </motion.div>
  );
};
