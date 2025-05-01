import DiscoverMoreSection from "../../components/home/DiscoverMoreSection";
import { HeroSection } from "../../components/home/HeroSection";
import RoomTypesSection from "../../components/home/RoomTypesSection";
import Footer from "../../layouts/Footer";

const Home = () => {
  
  return (
    <>
      <div
        className="relative min-h-screen w-screen overflow-hidden bg-fixed bg-center bg-cover"
        style={{ backgroundImage: `url('/imgs/home/homeBackground.jpg')` }}
      >
        <HeroSection />
        <RoomTypesSection />
        <DiscoverMoreSection />
      </div>
      <Footer
          direccion="La Fortuna, San Carlos, Costa Rica"
          direccionLink="https://maps.app.goo.gl/vytKdRRS3ERVsnLy8"
          correo="cabanassyc@gmail.com"
          telefono="+506 8543-5358"
          redes={{
            facebook: "https://facebook.com/cabanassyc",
            instagram: "https://instagram.com/cabanas_syc",
            booking: "https://www.booking.com/Share-Q4pFasj",
            whatsApp: "https://wa.me/50685435358"
          }}
          desarrollador={{
            nombre: "CastleeTech",
            url: "https://castleetech.dev"
          }}
        />
    </>
    
  );
};

export default Home;
