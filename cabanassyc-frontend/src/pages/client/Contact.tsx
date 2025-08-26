"use client";

import ContactSection from "../../components/contact/ContactSection";
import InfoSection from "../../components/contact/InfoSection";
import Footer from "../../layouts/Footer";

const Contact = () => {
  return (
    <>
      <main className="bg-[#4B2A1F] min-h-screen relative overflow-hidden">
        <ContactSection />
        <InfoSection />
        <div
          className="absolute bottom-0 left-0 w-full h-40 bg-gradient-to-t from-[#4B2A1F] to-transparent pointer-events-none z-10"
          aria-hidden="true"
        />
      </main>

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

export default Contact;
