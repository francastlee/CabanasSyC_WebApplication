import { NavLink } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

const NavLinks = () => {
  const { t } = useTranslation("navbar");

  const navItems = [
    { name: t("client.home"), path: "/home" },
    { name: t("client.rooms"), path: "/cabins" },
    { name: t("client.tours"), path: "/tours" },
    { name: t("client.facilities"), path: "/facilities" },
  ];

  return (
    <ul className="flex gap-6 text-white font-karla text-base">
      {navItems.map((item) => (
        <li key={item.name}>
          <NavLink
            to={item.path}
            className={({ isActive }) =>
              isActive ? 'text-yellow-400 font-semibold' : 'hover:text-yellow-400 transition'
            }
          >
            {item.name}
          </NavLink>
        </li>
      ))}
    </ul>
  );
};

export default NavLinks;
