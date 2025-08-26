import { NavLink } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../../contexts/AuthContext';
import { useTranslation } from 'react-i18next';
import LanguageSwitcher from './LanguageSwitcher';

interface MobileMenuProps {
  isOpen: boolean;
  onClose: () => void;
}

const MobileMenu = ({ isOpen, onClose }: MobileMenuProps) => {
  const { authenticated, logout } = useContext(AuthContext);
  const { t } = useTranslation("navbar");

  const navItems = [
    { name: t("client.home"), path: "/home" },
    { name: t("client.rooms"), path: "/cabins" },
    { name: t("client.tours"), path: "/tours" },
    { name: t("client.contact"), path: "/contact" },
  ];

  if (!isOpen) return null;

  return (
    <div className="md:hidden absolute left-0 right-0 mt-2 bg-gradient-to-r from-[#4B2A1F] via-[#6c5247] to-[#4B2A1F] rounded-xl shadow-lg backdrop-blur-md py-4 px-6 z-50">
      <ul className="flex flex-col gap-4 items-center text-white font-karla">
        {navItems.map((item) => (
          <li key={item.name}>
            <NavLink
              to={item.path}
              onClick={onClose}
              className={({ isActive }) =>
                isActive ? 'text-yellow-400 font-semibold' : 'hover:text-yellow-400 transition'
              }
            >
              {item.name}
            </NavLink>
          </li>
        ))}
        {authenticated ? (
          <>
            <li>
              <NavLink
                to="/profile"
                className="hover:text-yellow-400"
                onClick={onClose}
              >
                {t("client.profile")}
              </NavLink>
            </li>
            <li>
              <button
                onClick={() => {
                  logout();
                  onClose();
                }}
                className="text-white hover:text-yellow-400"
              >
                {t("client.logout")}
              </button>
            </li>
          </>
        ) : (
          <>
            <li>
              <NavLink
                to="/login"
                className="hover:text-yellow-400"
                onClick={onClose}
              >
                {t("client.login")}
              </NavLink>
            </li>
            <li>
              <NavLink
                to="/register"
                className="hover:text-yellow-400"
                onClick={onClose}
              >
                {t("client.register")}
              </NavLink>
            </li>
          </>
        )}
      </ul>

      <div className="mt-4 flex justify-center">
        <LanguageSwitcher />
      </div>
    </div>
  );
};

export default MobileMenu;
