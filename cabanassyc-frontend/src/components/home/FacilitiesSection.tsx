"use client";
import { useEffect, useRef, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { useTranslation } from "react-i18next";


interface Feature {
  title: string;
  description: string;
  sectionKey: string;
  images: string[]; 
  direction: string;
  icon: string;
  details: string [];
}

export default function FacilitiesSection() {
  
  const { t } = useTranslation("home"); 

const features: Feature[] = [
  {
    title:  t("facilities.poolTitle"),
    description: t("facilities.poolDescription"),
    sectionKey: "poolDetails",
    images: ["/imgs/home/pool.webp", "/imgs/home/pool2.webp", "/imgs/home/pool3.webp"],
    direction: "right",
    icon: "💡",
    details: ["size", "depth", "schedule", "extras"]
  },
  {
    title: t("facilities.volcanoTitle"),
    description: t("facilities.volcanoDescription"),
    sectionKey: "volcanoDetails",
    images: ["/imgs/home/volcano.webp", "/imgs/home/volcano2.webp", "/imgs/home/volcano3.webp"],
    direction: "left",
    icon: "🌋",
    details: ["time", "viewPoint", "extras"]
  },
  {
    title: t("facilities.kitchenTitle"),
    description: t("facilities.kitchenDescription"),
    sectionKey: "kitchenDetails",
    images: ["/imgs/home/ranch.webp", "/imgs/home/ranch2.webp", "/imgs/home/ranch3.webp"],
    direction: "right",
    icon: "🍴",
    details: ["capacity", "equipment", "amenities"]
  }
];


  const sectionRef = useRef<HTMLDivElement>(null);
  const [isVisible, setIsVisible] = useState(false);
  const [selectedFeature, setSelectedFeature] = useState<Feature | null>(null);
  const modalRef = useRef<HTMLDivElement>(null);
  const [windowSize, setWindowSize] = useState({
    width: typeof window !== 'undefined' ? window.innerWidth : 0,
    height: typeof window !== 'undefined' ? window.innerHeight : 0
  });

  useEffect(() => {
    const handleResize = () => {
      setWindowSize({
        width: window.innerWidth,
        height: window.innerHeight
      });
    };

    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  const isDesktop = windowSize.width >= 1024;
  const isTablet = windowSize.width >= 768 && windowSize.width < 1024;

  useEffect(() => {
    const observer = new IntersectionObserver(
      ([entry]) => {
        setIsVisible(entry.isIntersecting);
      },
      {
        root: null,
        rootMargin: "0px",
        threshold: 0.1,
      }
    );

    const currentSection = sectionRef.current;
    if (currentSection) {
      observer.observe(currentSection);
    }

    return () => {
      if (currentSection) {
        observer.unobserve(currentSection);
      }
    };
  }, []);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (modalRef.current && !modalRef.current.contains(event.target as Node)) {
        setSelectedFeature(null);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const FeatureCarousel = ({ images }: { images: string[] }) => {
    const [currentIndex, setCurrentIndex] = useState(0);
    const [isPlaying, setIsPlaying] = useState(true);
    const carouselRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
      if (!isPlaying) return;
      
      const interval = setInterval(() => {
        setCurrentIndex((prev) => (prev + 1) % images.length);
      }, 5000);

      return () => clearInterval(interval);
    }, [isPlaying, images.length]);

    const goToNext = () => {
      setIsPlaying(false);
      setCurrentIndex((prev) => (prev + 1) % images.length);
      setTimeout(() => setIsPlaying(true), 10000);
    };

    const goToPrev = () => {
      setIsPlaying(false);
      setCurrentIndex((prev) => (prev - 1 + images.length) % images.length);
      setTimeout(() => setIsPlaying(true), 10000);
    };

    const goToImage = (index: number) => {
      setIsPlaying(false);
      setCurrentIndex(index);
      setTimeout(() => setIsPlaying(true), 10000);
    };

    return (
      <div className="relative w-full h-full rounded-2xl overflow-hidden">
        <div ref={carouselRef} className="w-full h-full">
          <AnimatePresence>
            {images.map((image, index) => (
              currentIndex === index && (
                <motion.img
                  key={image}
                  src={image}
                  alt={`Imagen ${index + 1}`}
                  className="absolute inset-0 w-full h-full object-cover"
                  initial={{ opacity: 0 }}
                  animate={{ opacity: 1 }}
                  exit={{ opacity: 0 }}
                  transition={{ duration: 0.5 }}
                />
              )
            ))}
          </AnimatePresence>
        </div>

        <div className="absolute inset-0 flex items-center justify-between p-2">
          <button
            onClick={goToPrev}
            className="bg-black/30 hover:bg-black/50 rounded-full p-2 text-white transition-all transform hover:scale-110 z-10 cursor-pointer"
            aria-label="Imagen anterior"
          >
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
            </svg>
          </button>
          <button
            onClick={goToNext}
            className="bg-black/30 hover:bg-black/50 rounded-full p-2 text-white transition-all transform hover:scale-110 z-10 cursor-pointer"
            aria-label="Imagen siguiente"
          >
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
            </svg>
          </button>
        </div>

        <div className="absolute bottom-2 left-0 right-0 flex justify-center gap-1 z-10">
          {images.map((_, index) => (
            <button
              key={index}
              onClick={() => goToImage(index)}
              className={`w-2 h-2 rounded-full transition-all ${currentIndex === index ? 'bg-yellow-400 w-4' : 'bg-white/50 hover:bg-white/70 cursor-pointer'}`}
              aria-label={`Ir a imagen ${index + 1}`}
            />
          ))}
        </div>

        <div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent" />
      </div>
    );
  };

  return (
    <section
      ref={sectionRef}
      className="w-full bg-[#1a3a17] pb-50 pt-20 px-4 flex flex-col items-center gap-16 relative overflow-hidden select-none"
    >

      <motion.h2
        className="text-white text-4xl md:text-5xl font-cinzel font-bold text-center relative"
        initial={{ opacity: 0, y: 20 }}
        animate={isVisible ? { opacity: 1, y: 0 } : {}}
        transition={{ duration: 0.8 }}
      >
        {t("facilities.title")}
      </motion.h2>

      <div className="flex flex-col gap-16 md:gap-28 max-w-6xl w-full relative z-10">
        {features.map((feature, index) => {
          const fromRight = feature.direction === "right";

          const containerVariants = {
            hidden: { 
              opacity: 0, 
              x: fromRight ? 150 : -150,
              rotate: fromRight ? 2 : -2 
            },
            visible: {
              opacity: 1,
              x: 0,
              rotate: 0,
              transition: {
                duration: 0.8,
                delay: index * 0.3,
                ease: [0.16, 1, 0.3, 1],
              },
            },
          };

          const imageVariants = {
            hidden: { scale: 0.9, opacity: 0 },
            visible: {
              scale: 1,
              opacity: 1,
              transition: {
                duration: 0.6,
                delay: index * 0.3 + 0.2,
              },
            },
          };

          return (
            <motion.div
              key={feature.title}
              className={`flex flex-col-reverse ${isDesktop ? 'md:flex-row' : ''} items-center gap-8 md:gap-14 ${
                !fromRight && isDesktop ? "md:flex-row-reverse" : ""
              }`}
              initial="hidden"
              animate={isVisible ? "visible" : "hidden"}
              variants={containerVariants}
            >
              <motion.div 
                className={`
                  relative 
                  ${isDesktop ? 'md:w-1/2 h-[500px]' : isTablet ? 'w-full h-[350px]' : 'w-full aspect-video'}
                  rounded-2xl overflow-hidden shadow-2xl
                `}
                whileHover={{ y: isDesktop ? -10 : 0 }}
                transition={{ type: "spring", stiffness: 300 }}
                variants={imageVariants}
              >
                <FeatureCarousel images={feature.images} />
              </motion.div>

              <motion.div 
                className={`
                  ${isDesktop ? 'md:w-1/2' : 'w-full'}
                  ${isDesktop ? '' : 'mt-6'}
                `}
                initial={{ opacity: 0 }}
                animate={isVisible ? { opacity: 1 } : {}}
                transition={{ delay: index * 0.3 + 0.4 }}
              >
                <div className={`
                  bg-white/5 backdrop-blur-sm 
                  ${isDesktop ? 'p-8' : 'p-6'} 
                  rounded-2xl border border-white/10 shadow-lg
                `}>
                  <div className="text-4xl mb-4">{feature.icon}</div>
                  <h3 className="text-2xl md:text-3xl font-bold mb-4 font-cinzel text-white">
                    {feature.title}
                  </h3>
                  <p className="text-slate-100 text-base md:text-lg leading-relaxed mb-6">
                    {feature.description}
                  </p>
                  <motion.button
                    className="px-6 py-3 bg-gradient-to-r from-yellow-500 to-yellow-600 text-white rounded-full text-sm font-medium shadow-lg cursor-pointer"
                    whileHover={{ scale: 1.05 }}
                    whileTap={{ scale: 0.95 }}
                    onClick={() => setSelectedFeature(feature)}
                  >
                    {t("facilities.button")}
                  </motion.button>
                </div>
              </motion.div>
            </motion.div>
          );
        })}
      </div>

      <img 
          src="/imgs/home/frog.webp" 
          alt="Rana de ojos rojos" 
          className="absolute xl:top-58 top-33 xl:left-64 left-0 w-20 xl:w-48 z-20 pointer-events-none"
        />
        <img 
          src="/imgs/home/hoja.webp" 
          alt="Rana de ojos rojos" 
          className="absolute xl:top-35 top-35 xl:left-25 left-3 w-10 xl:w-48 z-20 pointer-events-none hoja-animation1 "
          style={{ transform: "rotate(70deg)" }} 
        />
        <img 
          src="/imgs/home/hoja.webp" 
          alt="Rana de ojos rojos" 
          className="absolute xl:top-0 top-40 xl:left-20 left-3 w-10 xl:w-48 z-20 pointer-events-none hoja-animation2 "
          style={{ transform: "rotate(60deg)" }} 
        />

      <AnimatePresence>
        {selectedFeature && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 bg-black/70 z-50 flex items-center justify-center p-4"
          >
            <motion.div
              ref={modalRef}
              initial={{ scale: 0.9, y: 50 }}
              animate={{ scale: 1, y: 0 }}
              exit={{ scale: 0.9, y: 50 }}
              className="bg-gradient-to-br from-[#0f2a15] to-[#1a3a17] rounded-2xl max-w-4xl w-full mx-4 shadow-2xl border border-white/10 relative"
            >
              <button
                onClick={() => setSelectedFeature(null)}
                className="absolute top-4 right-4 text-white/70 hover:text-white text-2xl cursor-pointer z-10"
                aria-label="Cerrar modal"
              >
                &times;
              </button>

              <div className="flex flex-col lg:flex-row">
                <div className="lg:w-1/2 relative">
                  <div className="aspect-[4/3] w-full">
                    <img
                      src={selectedFeature.images[0]} 
                      alt={selectedFeature.title}
                      className="absolute inset-0 w-full h-full object-cover rounded-t-xl lg:rounded-l-xl lg:rounded-tr-none shadow-lg"
                      loading="lazy"
                      decoding="async"
                    />
                  </div>
                </div>
                <div className="lg:w-1/2 p-6 md:p-8 overflow-y-auto max-h-[70vh] lg:max-h-[80vh]">
                  <div className="text-4xl mb-4">{selectedFeature.icon}</div>
                  <h3 className="text-3xl font-bold mb-4 font-cinzel text-white">
                    {selectedFeature.title}
                  </h3>
                  <p className="text-slate-100 text-lg mb-6">
                    {selectedFeature.description}
                  </p>

                  <div className="space-y-4">
                    {selectedFeature.details.map((key) => (
                      <div key={key} className="border-l-2 border-amber-400 pl-4">
                        <h4 className="text-amber-400 font-semibold capitalize">
                          {t(`facilities.${selectedFeature.sectionKey}.${key}Title`)}
                        </h4>
                        <p className="text-white">
                          {t(`facilities.${selectedFeature.sectionKey}.${key}Data`)}
                        </p>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
      <div className="absolute bottom-0 left-0 w-full h-40 bg-gradient-to-t from-[#4B2A1F] to-transparent pointer-events-none"></div>
    </section>
  );
}