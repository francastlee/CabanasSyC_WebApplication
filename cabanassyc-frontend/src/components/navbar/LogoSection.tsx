import { Link } from 'react-router-dom';

const LogoSection = () => {
  return (
    <Link to="/home" className="flex items-center gap-2">
      <img src="/imgs/common/logoSyC.png" alt="Logo Cabañas SyC" className="h-8 w-auto" />
      <span className="text-white font-merriweather text-lg">Cabañas SyC</span>
    </Link>
  );
};

export default LogoSection;
