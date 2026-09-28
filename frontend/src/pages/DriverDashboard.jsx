import { useEffect, useState } from "react";
import api from "../services/api";

const statuses = [
    "CREATED",
    "PICKED_UP",
    "IN_TRANSIT",
    "OUT_FOR_DELIVERY",
    "DELIVERED",
    "DELAYED",
];

function DriverDashboard() {
    const [shipments, setShipments] = useState([]);
    const [message, setMessage] = useState("");

    const [selectedStatuses, setSelectedStatuses] = useState({});
    const [notes, setNotes] = useState({});

    useEffect(() => {
        fetchShipments();
    }, []);

    const fetchShipments = async () => {
        try {
            const response = await api.get("/shipments");
            setShipments(response.data);
            setMessage("");
        } catch (error) {
            setMessage(
                error.response?.data?.error ||
                "Failed to load shipments"
            );
        }
    };

    const handleStatusChange = (shipmentId, status) => {
        setSelectedStatuses((previous) => ({
            ...previous,
            [shipmentId]: status,
        }));
    };

    const handleNoteChange = (shipmentId, note) => {
        setNotes((previous) => ({
            ...previous,
            [shipmentId]: note,
        }));
    };

    const handleUpdate = async (shipmentId) => {
        const status =
            selectedStatuses[shipmentId];

        if (!status) {
            setMessage("Please select a status.");
            return;
        }

        try {
            await api.put(
                `/shipments/${shipmentId}/status`,
                {
                    status: status,
                    note: notes[shipmentId] || "",
                }
            );

            setMessage(
                `Shipment #${shipmentId} updated successfully!`
            );

            fetchShipments();

        } catch (error) {
            setMessage(
                error.response?.data?.error ||
                "Failed to update shipment"
            );
        }
    };

    const inTransitCount = shipments.filter(
        (shipment) =>
            shipment.currentStatus === "IN_TRANSIT"
    ).length;

    const outForDeliveryCount = shipments.filter(
        (shipment) =>
            shipment.currentStatus === "OUT_FOR_DELIVERY"
    ).length;

    const deliveredCount = shipments.filter(
        (shipment) =>
            shipment.currentStatus === "DELIVERED"
    ).length;

    return (
        <main className="dashboard-page">

            <section className="dashboard-header">

                <div>
                    <p className="eyebrow">
                        DRIVER OPERATIONS
                    </p>

                    <h1>Driver Dashboard</h1>

                    <p>
                        Manage assigned shipments and update
                        delivery status in real time.
                    </p>
                </div>

            </section>

            {/* Driver Statistics */}

            <section className="stats-grid">

                <div className="stat-card">
                    <p>Assigned Shipments</p>
                    <h2>{shipments.length}</h2>
                </div>

                <div className="stat-card">
                    <p>In Transit</p>
                    <h2>{inTransitCount}</h2>
                </div>

                <div className="stat-card">
                    <p>Out for Delivery</p>
                    <h2>{outForDeliveryCount}</h2>
                </div>

                <div className="stat-card">
                    <p>Delivered</p>
                    <h2>{deliveredCount}</h2>
                </div>

            </section>

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

            {/* Shipment Updates */}

            <section className="dashboard-card">

                <div className="section-heading">

                    <div>
                        <h2>Assigned Shipments</h2>

                        <p>
                            Update shipment status and delivery notes.
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
                        No shipments assigned.
                    </div>

                ) : (

                    <div className="table-container">

                        <table className="dashboard-table">

                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Tracking Code</th>
                                    <th>Current Status</th>
                                    <th>ETA</th>
                                    <th>New Status</th>
                                    <th>Note</th>
                                    <th>Action</th>
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
                                                ? new Date(
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

                                        <td>
                                            <select
                                                className="status-select"
                                                value={
                                                    selectedStatuses[
                                                        shipment.id
                                                    ] ||
                                                    shipment.currentStatus
                                                }
                                                onChange={(event) =>
                                                    handleStatusChange(
                                                        shipment.id,
                                                        event.target.value
                                                    )
                                                }
                                            >

                                                {statuses.map(
                                                    (status) => (
                                                        <option
                                                            key={status}
                                                            value={status}
                                                        >
                                                            {status.replaceAll(
                                                                "_",
                                                                " "
                                                            )}
                                                        </option>
                                                    )
                                                )}

                                            </select>
                                        </td>

                                        <td>
                                            <input
                                                className="note-input"
                                                type="text"
                                                placeholder="Add note"
                                                value={
                                                    notes[
                                                        shipment.id
                                                    ] || ""
                                                }
                                                onChange={(event) =>
                                                    handleNoteChange(
                                                        shipment.id,
                                                        event.target.value
                                                    )
                                                }
                                            />
                                        </td>

                                        <td>
                                            <button
                                                className="primary-button update-button"
                                                onClick={() =>
                                                    handleUpdate(
                                                        shipment.id
                                                    )
                                                }
                                            >
                                                Update
                                            </button>
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

export default DriverDashboard;