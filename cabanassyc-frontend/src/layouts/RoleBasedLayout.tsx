import NavbarClient from "./NavbarClient.tsx";
import { useAuth } from "../hooks/UseAuth.ts";
import { Outlet } from "react-router-dom";
import LoadingScreen from "../components/common/LoadingScreen";
function RoleBasedLayout() {
  const { user, loading } = useAuth();

  if (loading) {
    return <LoadingScreen />;
  }

  return (
    <div className="min-h-screen flex flex-col">
      {!user && <ClientNavbar />}
      {user?.role === "ADMIN" && <AdminNavbar />}
      {user?.role === "WORKER" && <WorkerNavbar />}
      {user?.role === "USER" && <ClientNavbar />}

      <main>
        <Outlet />
      </main>
    </div>
  );
}

export default RoleBasedLayout;

const AdminNavbar = () => (
  <nav className="bg-blue-700 text-white p-4 flex justify-between">
    <div>Admin Panel</div>
    <div>Dashboard | Users | Reports</div>
  </nav>
);

const WorkerNavbar = () => (
  <nav className="bg-green-700 text-white p-4 flex justify-between">
    <div>Worker Panel</div>
    <div>My Tasks | Work Days</div>
  </nav>
);

const ClientNavbar = () => <NavbarClient />;
