import { useEffect, useState } from "react";
import api from "../services/api";

function AdminDashboard() {
    const [shipments, setShipments] = useState([]);

    const [trackingCode, setTrackingCode] = useState("");
    const [customerId, setCustomerId] = useState("");
    const [driverId, setDriverId] = useState("");
    const [routeId, setRouteId] = useState("");

    const [message, setMessage] = useState("");

    useEffect(() => {
        fetchShipments();
    }, []);

    const fetchShipments = async () => {
        try {
            const response = await api.get("/shipments");
            setShipments(response.data);
        } catch (error) {
            setMessage(
                error.response?.data?.error ||
                "Failed to load shipments"
            );
        }
    };

    const handleCreateShipment = async (event) => {
        event.preventDefault();

        setMessage("");

        try {
            await api.post("/shipments", {
                trackingCode: trackingCode,
                customerId: Number(customerId),
                driverId: Number(driverId),
                routeId: Number(routeId),
            });

            setMessage("Shipment created successfully!");

            setTrackingCode("");
            setCustomerId("");
            setDriverId("");
            setRouteId("");

            fetchShipments();

        } catch (error) {
            setMessage(
                error.response?.data?.error ||
                "Failed to create shipment"
            );
        }
    };

    const totalShipments = shipments.length;

    const inTransitShipments = shipments.filter(
        (shipment) =>
            shipment.currentStatus === "IN_TRANSIT"
    ).length;

    const deliveredShipments = shipments.filter(
        (shipment) =>
            shipment.currentStatus === "DELIVERED"
    ).length;

    const outForDeliveryShipments = shipments.filter(
        (shipment) =>
            shipment.currentStatus === "OUT_FOR_DELIVERY"
    ).length;

    return (
        <main className="dashboard-page">

            <section className="dashboard-header">
                <div>
                    <p className="eyebrow">
                        OPERATIONS
                    </p>

                    <h1>Admin Dashboard</h1>

                    <p>
                        Manage shipments and monitor delivery activity.
                    </p>
                </div>
            </section>

            {/* Summary Cards */}

            <section className="stats-grid">

                <div className="stat-card">
                    <p>Total Shipments</p>
                    <h2>{totalShipments}</h2>
                </div>

                <div className="stat-card">
                    <p>In Transit</p>
                    <h2>{inTransitShipments}</h2>
                </div>

                <div className="stat-card">
                    <p>Out for Delivery</p>
                    <h2>{outForDeliveryShipments}</h2>
                </div>

                <div className="stat-card">
                    <p>Delivered</p>
                    <h2>{deliveredShipments}</h2>
                </div>

            </section>

            {/* Create Shipment */}

            <section className="dashboard-card">

                <div className="section-heading">
                    <div>
                        <h2>Create Shipment</h2>

                        <p>
                            Create and assign a new shipment.
                        </p>
                    </div>
                </div>

                <form
                    className="shipment-form"
                    onSubmit={handleCreateShipment}
                    autoComplete="off"
                >

                    <div className="form-group">
                        <label htmlFor="trackingCode">
                            Tracking Code
                        </label>

                        <input
                            id="trackingCode"
                            type="text"
                            name="new-shipment-tracking-code"
                            autoComplete="new-password"
                            value={trackingCode}
                            onChange={(event) =>
                                setTrackingCode(event.target.value)
                            }
                            placeholder="e.g. TP-CHBL-2026-003"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="customerId">
                            Customer ID
                        </label>

                        <input
                            id="customerId"
                            type="number"
                            name="new-shipment-customer-id"
                            autoComplete="off"
                            value={customerId}
                            onChange={(event) =>
                                setCustomerId(event.target.value)
                            }
                            placeholder="Enter customer ID"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="driverId">
                            Driver ID
                        </label>

                        <input
                            id="driverId"
                            type="number"
                            name="new-shipment-driver-id"
                            autoComplete="off"
                            value={driverId}
                            onChange={(event) =>
                                setDriverId(event.target.value)
                            }
                            placeholder="Enter driver ID"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="routeId">
                            Route ID
                        </label>

                        <input
                            id="routeId"
                            type="number"
                            name="new-shipment-route-id"
                            autoComplete="off"
                            value={routeId}
                            onChange={(event) =>
                                setRouteId(event.target.value)
                            }
                            placeholder="Enter route ID"
                            required
                        />
                    </div>

                    <div className="form-action">
                        <button
                            className="primary-button"
                            type="submit"
                        >
                            Create Shipment
                        </button>
                    </div>

                </form>

                {message && (
                    <div
                        className={
                            message.includes("successfully")
                                ? "success-message"
                                : "error-message"
                        }
                    >
                        {message}
                    </div>
                )}

            </section>

            {/* Shipments Table */}

            <section className="dashboard-card">

                <div className="section-heading">
                    <div>
                        <h2>Shipments</h2>

                        <p>
                            Current shipment activity across TrackPlus.
                        </p>
                    </div>

                    <button
                        className="secondary-button"
                        onClick={fetchShipments}
                    >
                        Refresh
                    </button>
                </div>

                {shipments.length === 0 ? (
                    <div className="empty-state">
                        No shipments found.
                    </div>
                ) : (
                    <div className="table-container">

                        <table className="dashboard-table">

                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Tracking Code</th>
                                    <th>Status</th>
                                    <th>Estimated Delivery</th>
                                </tr>
                            </thead>

                            <tbody>

                                {shipments.map((shipment) => (

                                    <tr key={shipment.id}>

                                        <td>
                                            #{shipment.id}
                                        </td>

                                        <td className="tracking-code">
                                            {shipment.trackingCode}
                                        </td>

                                        <td>
                                            <span
                                                className={`status-badge status-${shipment.currentStatus.toLowerCase()}`}
                                            >
                                                {shipment.currentStatus.replaceAll(
                                                    "_",
                                                    " "
                                                )}
                                            </span>
                                        </td>

                                        <td>
                                            {shipment.estimatedDeliveryAt
                                                ?
                                             new Date(
                                                 shipment.estimatedDeliveryAt
                                             ).toLocaleString(
                                                 "en-US",
                                                 {
                                                     month: "short",
                                                     day: "numeric",
                                                     year: "numeric",
                                                     hour: "numeric",
                                                     minute: "2-digit",
                                                     hour12: true,
                                                 }
                                            )
                                                : "N/A"}
                                        </td>

                                    </tr>

                                ))}

                            </tbody>

                        </table>

                    </div>
                )}

            </section>

        </main>
    );
}

export default AdminDashboard;