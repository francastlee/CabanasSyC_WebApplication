"use client";
interface StarsBackgroundProps {
  starCount?: number;
}

export const StarsBackground = ({ starCount = 100 }: StarsBackgroundProps) => (
  <div 
    className="hidden lg:block stars z-0 absolute inset-0 h-full pointer-events-none overflow-hidden"
    aria-hidden="true"
  >
    {Array.from({ length: starCount }).map((_, i) => {
      const size = Math.random() * 2 + 1;
      return (
        <div
          key={`star-${i}`}
          className="absolute rounded-full bg-white animate-pulse"
          style={{
            top: `${Math.random() * 100}%`,
            left: `${Math.random() * 100}%`,
            width: `${size}px`,
            height: `${size}px`,
            animationDelay: `${Math.random() * 6}s`,
            opacity: Math.random() * 0.5 + 0.3,
          }}
        />
      );
    })}
  </div>
);