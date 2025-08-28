"use client";
import { useMemo, memo } from "react";

interface StarsBackgroundProps {
  starCount?: number;
}

export const StarsBackground = memo(({ starCount = 100 }: StarsBackgroundProps) => {
  const stars = useMemo(() => {
    return Array.from({ length: starCount }).map(() => {
      const size = Math.random() * 2 + 1;
      return {
        top: `${Math.random() * 100}%`,
        left: `${Math.random() * 100}%`,
        size,
        delay: `${Math.random() * 6}s`,
        opacity: Math.random() * 0.5 + 0.3,
      };
    });
  }, [starCount]);

  return (
    <div
      className="hidden lg:block stars z-0 absolute inset-0 h-full pointer-events-none overflow-hidden"
      aria-hidden="true"
    >
      {stars.map((s, i) => (
        <div
          key={i}
          className="absolute rounded-full bg-white animate-pulse"
          style={{
            top: s.top,
            left: s.left,
            width: `${s.size}px`,
            height: `${s.size}px`,
            animationDelay: s.delay,
            opacity: s.opacity,
          }}
        />
      ))}
    </div>
  );
});
