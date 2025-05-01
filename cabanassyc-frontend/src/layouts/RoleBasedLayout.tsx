import NavbarClient from "./NavbarClient.tsx";
import { useAuth } from "../hooks/UseAuth.ts";
import { Outlet } from "react-router-dom";

function RoleBasedLayout() {
  const { user } = useAuth();

  if (!user) {
    return <div>Loading...</div>;
  }

  return (
    <div className="min-h-screen flex flex-col">
      {/* Navbar diferente según el rol */}
      {user.role === "ADMIN" && <AdminNavbar />}
      {user.role === "WORKER" && <WorkerNavbar />}
      {user.role === "USER" && <ClientNavbar />}

      {/* Contenido principal */}
      <main className="flex-1 bg-gray-100">
        <Outlet />
      </main>
    </div>
  );
}

export default RoleBasedLayout;

// Componente Navbar de Admin
const AdminNavbar = () => (
  <nav className="bg-blue-700 text-white p-4 flex justify-between">
    <div>Admin Panel</div>
    <div>Dashboard | Users | Reports</div>
  </nav>
);

// Componente Navbar de Trabajador
const WorkerNavbar = () => (
  <nav className="bg-green-700 text-white p-4 flex justify-between">
    <div>Worker Panel</div>
    <div>My Tasks | Work Days</div>
  </nav>
);

// Componente Navbar de Cliente
const ClientNavbar = () => (
  <NavbarClient />
);