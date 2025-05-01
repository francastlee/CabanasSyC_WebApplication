import { useState } from 'react';
import { FaBars } from 'react-icons/fa';
import { useRef } from 'react';

import LogoSection from '../components/navbar/LogoSection';
import NavLinks from '../components/navbar/NavLinks';
import UserDropdown from '../components/navbar/UserDropdown'; 
import MobileMenu from '../components/navbar/MobileMenu'; 
import LanguageSwitcher from '../components/navbar/LanguageSwitcher'; 

const NavbarClient = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const dropdownRef = useRef(null);

  return (
    <header className="fixed top-4 left-1/2 transform -translate-x-1/2 w-full max-w-[95%] md:max-w-screen-xl z-50 px-4 select-none">
      <nav className="flex items-center justify-between font-noto px-6 py-3 rounded-full shadow-lg backdrop-blur-md bg-gradient-to-r from-[#4B2A1F] via-[#6c5247] to-[#4B2A1F]">
        
        <LogoSection />

        <div className="hidden md:flex gap-6 items-center">
          <NavLinks />
          <LanguageSwitcher />
        </div>

        <div className="relative hidden md:flex" ref={dropdownRef}>
          <UserDropdown />
        </div>

        <div className="md:hidden">
          <button onClick={() => setMenuOpen(!menuOpen)} className="text-white">
            <FaBars />
          </button>
        </div>
      </nav>

      <MobileMenu isOpen={menuOpen} onClose={() => setMenuOpen(false)} />
    </header>
  );
};

export default NavbarClient;
