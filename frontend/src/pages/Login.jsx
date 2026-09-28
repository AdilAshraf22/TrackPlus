import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";

function Login() {
    const { login } = useAuth();
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleLogin = async (event) => {
        event.preventDefault();

        setMessage("");

        try {
            const response = await api.post("/auth/login", {
                email,
                password,
            });

            const token = response.data;

            login(token);

            const user = JSON.parse(
                atob(
                    token
                        .split(".")[1]
                        .replace(/-/g, "+")
                        .replace(/_/g, "/")
                )
            );

            if (user.role === "ADMIN") {
                navigate("/admin");
            } else if (user.role === "DRIVER") {
                navigate("/driver");
            } else {
                navigate("/");
            }

        } catch (error) {
            setMessage(
                error.response?.data?.error ||
                "Login failed. Please check your email and password."
            );
        }
    };

    return (
        <main className="login-page">

            <section className="login-card">

                <div className="login-header">
                    <div className="login-logo">
                        TrackPlus
                    </div>

                    <p className="eyebrow">
                        LOGISTICS MANAGEMENT PLATFORM
                    </p>

                    <h1>Welcome Back</h1>

                    <p className="login-subtitle">
                        Sign in to manage shipments and track deliveries.
                    </p>
                </div>

                <form
                    className="login-form"
                    onSubmit={handleLogin}
                    autoComplete="on"
                >

                    <div className="form-group">
                        <label htmlFor="email">
                            Email
                        </label>

                        <input
                            id="email"
                            type="email"
                            value={email}
                            onChange={(event) =>
                                setEmail(event.target.value)
                            }
                            placeholder="Enter your email"
                            autoComplete="email"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">
                            Password
                        </label>

                        <input
                            id="password"
                            type="password"
                            value={password}
                            onChange={(event) =>
                                setPassword(event.target.value)
                            }
                            placeholder="Enter your password"
                            autoComplete="current-password"
                            required
                        />
                    </div>

                    <button
                        className="login-button"
                        type="submit"
                    >
                        Sign In
                    </button>

                </form>

                {message && (
                    <div className="login-error">
                        {message}
                    </div>
                )}

                <div className="login-footer">
                    Customer?
                    <button
                        type="button"
                        onClick={() => navigate("/")}
                    >
                        Track your shipment
                    </button>
                </div>

            </section>

        </main>
    );
}

export default Login;