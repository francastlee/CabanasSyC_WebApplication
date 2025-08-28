import ScrollToHash from "../../components/common/ScrollToHash";
import DiscoverMoreSection from "../../components/home/DiscoverMoreSection";
import FacilitiesSection from "../../components/home/FacilitiesSection";
import { HeroSection } from "../../components/home/HeroSection";
import RoomTypesSection from "../../components/home/RoomTypesSection";
import Footer from "../../layouts/Footer";

const Home = () => {
  return (
    <>
      <ScrollToHash offset={0} />
      <section className="relative min-h-screen w-full overflow-hidden">
        <img
          src="/imgs/home/homeBackground.webp"
          alt="Fondo de Cabañas SyC"
          width="1920"
          height="1080"
          className="absolute inset-0 w-full h-full object-cover object-center"
          loading="eager"
          fetchPriority="high"
        />

        <div className="relative z-10">
          <HeroSection />
        </div>
      </section>

      <RoomTypesSection />
      <DiscoverMoreSection />
      <FacilitiesSection />

      <Footer
        direccion="La Fortuna, San Carlos, Costa Rica"
        direccionLink="https://maps.app.goo.gl/vytKdRRS3ERVsnLy8"
        correo="cabanassyc@gmail.com"
        telefono="+506 8543-5358"
        color="#4B2A1F"
        redes={{
          facebook: "https://facebook.com/cabanassyc",
          instagram: "https://instagram.com/cabanas_syc",
          booking: "https://www.booking.com/Share-Q4pFasj",
          whatsApp: "https://wa.me/50685435358",
        }}
        desarrollador={{
          nombre: "CastleeTech",
          url: "https://castleetech.dev",
        }}
      />
    </>
  );
};

export default Home;
