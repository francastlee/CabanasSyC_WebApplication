import { createContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginRequest, fetchUserData } from '../api/Auth';
import { toast } from 'react-toastify';
import LoadingScreen from '../components/common/LoadingScreen';

interface User {
    id: number;
    name: string;
    email: string;
    role : string;
}

interface AuthContextType {
    user: User | null;
    loading: boolean;
    login: (email: string, password: string) => Promise<void>;
    logout: () => void;
    authenticated: boolean;
}

interface AuthProviderProps {
    children: React.ReactNode;
}

export const AuthContext = createContext<AuthContextType>({} as AuthContextType);

export const AuthProvider = ({ children }: AuthProviderProps) => {
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            getUser(token);
        } else {
            setLoading(false);
        }
    }, []);

    const getUser = async (token: string) => {
        try {
            const userData = await fetchUserData(token);
            setUser(userData);
            return userData;
        } catch (error) {
            console.error('Error fetching user:', error);
            logout();
        } finally {
            setLoading(false);
        }
    };

    const login = async (email: string, password: string) => {
        try {
            const token = await loginRequest(email, password);
            localStorage.setItem('token', token);
            const user = await getUser(token);

            toast.success(`¡Bienvenido, ${user.name}! 🎉`);
            if (user.role === "ADMIN") {
                navigate('/admin/dashboard');
            } else if (user.role === "WORKER") {
                navigate('/worker/dashboard');
            } else if (user.role === "USER") {
                navigate('/home');
            } else {
                navigate('/login');
            }
        } catch (error: any) {
            console.error('Login error:', error);
            toast.error(error.response?.data?.message || 'Login failed. Please check your credentials.');
            throw new Error(error.response?.data?.message || 'Login failed. Please check your credentials.');
        }
    };

    const logout = () => {
        localStorage.removeItem('token');
        setUser(null);
        navigate('/login');
    };

    return (
        <AuthContext.Provider value={{ user, loading, login, logout, authenticated: !!user }}>
            {loading ? <LoadingScreen /> : children}
        </AuthContext.Provider>
    );
};
