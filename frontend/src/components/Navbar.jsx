import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {
    const { user, isAuthenticated, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    return (
        <nav className="navbar">
            <div className="navbar-brand">
                <Link to="/">TrackPlus</Link>
            </div>

            <div className="navbar-links">
                <Link to="/">Track Shipment</Link>

                {isAuthenticated && user?.role === "ADMIN" && (
                    <Link to="/admin">Admin Dashboard</Link>
                )}

                {isAuthenticated && user?.role === "DRIVER" && (
                    <Link to="/driver">Driver Dashboard</Link>
                )}

                {isAuthenticated ? (
                    <>
                        <span className="user-info">
                            {user?.name} ({user?.role})
                        </span>

                        <button
                            className="logout-button"
                            onClick={handleLogout}
                        >
                            Logout
                        </button>
                    </>
                ) : (
                    <Link to="/login">Login</Link>
                )}
            </div>
        </nav>
    );
}

export default Navbar;