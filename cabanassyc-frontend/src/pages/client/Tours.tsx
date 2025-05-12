import TourList from "../../components/tours/TourList";
import Footer from "../../layouts/Footer";

const Tours = () => {
  return (
    <>
      <main className="min-h-screen relative">
        <TourList />
      </main>
      <Footer
        direccion="La Fortuna, San Carlos, Costa Rica"
        direccionLink="https://maps.app.goo.gl/vytKdRRS3ERVsnLy8"
        correo="cabanassyc@gmail.com"
        telefono="+506 8543-5358"
        color='#1a3a17'
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

export default Tours;
