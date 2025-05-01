import { useContext, useRef, useEffect, useState } from 'react';
import { FaUserCircle } from 'react-icons/fa';
import { AuthContext } from '../../contexts/AuthContext';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';

const UserDropdown = () => {
  const { user, authenticated, logout } = useContext(AuthContext);
  const { t } = useTranslation('navbar');
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        dropdownRef.current &&
        !(dropdownRef.current as any).contains(event.target)
      ) {
        setDropdownOpen(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  return (
    <div className="relative hidden md:flex" ref={dropdownRef}>
      <button
        onClick={() => setDropdownOpen(!dropdownOpen)}
        className="flex items-center gap-2 text-white ml-4 icon-glow-green"
        aria-label="Usuario"
      >
        <FaUserCircle className="text-2xl" />
      </button>

      {dropdownOpen && (
        <div className="absolute right-0 mt-12 w-44 bg-[#4B2A1F] rounded-md shadow-lg py-2 z-50">
          {authenticated ? (
            <>
              {user?.name && (
                <div className="px-4 py-2 text-sm text-white border-b border-gray-200">
                  {user.name}
                </div>
              )}
              <Link
                to="/profile"
                className="block px-4 py-2 text-sm text-white hover:bg-[#856D5D]"
              >
                {t('client.profile')}
              </Link>
              <button
                onClick={logout}
                className="w-full text-left px-4 py-2 text-sm text-white cursor-pointer hover:bg-[#856D5D]"
              >
                {t('client.logout')}
              </button>
            </>
          ) : (
            <>
              <Link
                to="/login"
                className="block px-4 py-2 text-sm text-white hover:bg-[#856D5D]"
              >
                {t('client.login')}
              </Link>
              <Link
                to="/register"
                className="block px-4 py-2 text-sm text-white hover:bg-[#856D5D]"
              >
                {t('client.register')}
              </Link>
            </>
          )}
        </div>
      )}
    </div>
  );
};

export default UserDropdown;
