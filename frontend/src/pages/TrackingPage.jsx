import { useEffect, useRef, useState } from "react";
import { Client } from "@stomp/stompjs";
import api from "../services/api";

function TrackingPage() {
    const [trackingCode, setTrackingCode] = useState("");
    const [shipment, setShipment] = useState(null);
    const [message, setMessage] = useState("");

    const stompClient = useRef(null);

    const handleTrack = async (event) => {
        event.preventDefault();

        try {
            const response = await api.get(
                `/shipments/track/${trackingCode}`
            );

            setShipment(response.data);
            setMessage("");

        } catch (error) {
            setShipment(null);

            setMessage(
                error.response?.data?.error ||
                "Shipment not found"
            );
        }
    };

    useEffect(() => {
        if (!shipment) {
            return;
        }

        const client = new Client({
            brokerURL: "ws://localhost:8080/ws",

            onConnect: () => {
                console.log("Connected to TrackPlus WebSocket");

                client.subscribe(
                    `/topic/shipment/${shipment.id}`,
                    (message) => {
                        const updatedShipment =
                            JSON.parse(message.body);

                        setShipment((currentShipment) => ({
                            ...currentShipment,
                            currentStatus:
                                updatedShipment.status,
                            estimatedDeliveryAt:
                                updatedShipment.estimatedDeliveryAt,
                        }));
                    }
                );

                console.log(
                    `Subscribed to shipment ${shipment.id}`
                );
            },

            onStompError: (frame) => {
                console.error(
                    "WebSocket STOMP error:",
                    frame.headers["message"]
                );
            },

            onWebSocketError: () => {
                console.error(
                    "WebSocket connection error"
                );
            },
        });

        stompClient.current = client;

        client.activate();

        return () => {
            client.deactivate();
            stompClient.current = null;
        };
    }, [shipment?.id]);

    return (
        <main className="tracking-page">

            <section className="tracking-hero">

                <div className="tracking-header">
                    <p className="eyebrow">
                        REAL-TIME LOGISTICS
                    </p>

                    <h1>
                        Track Your Shipment
                    </h1>

                    <p className="tracking-subtitle">
                        Enter your tracking code to see the latest
                        shipment status and estimated delivery time.
                    </p>
                </div>

                <form
                    className="tracking-form"
                    onSubmit={handleTrack}
                >
                    <label htmlFor="trackingCode">
                        Tracking Code
                    </label>

                    <div className="tracking-input-row">

                        <input
                            id="trackingCode"
                            type="text"
                            value={trackingCode}
                            onChange={(event) =>
                                setTrackingCode(event.target.value)
                            }
                            placeholder="e.g. TP-XXXX-XXXX"
                            autoComplete="off"
                            required
                        />

                        <button type="submit">
                            Track Shipment
                        </button>

                    </div>
                </form>

                {message && (
                    <div className="tracking-error">
                        {message}
                    </div>
                )}

                {shipment && (
                    <section className="shipment-card">

                        <div className="shipment-card-header">

                            <div>
                                <p className="card-label">
                                    TRACKING CODE
                                </p>

                                <h2>
                                    {shipment.trackingCode}
                                </h2>
                            </div>

                            <span
                                className={`status-badge status-${shipment.currentStatus.toLowerCase()}`}
                            >
                                {shipment.currentStatus.replaceAll(
                                    "_",
                                    " "
                                )}
                            </span>

                        </div>

                        <div className="shipment-details">

                            <div className="detail-box">

                                <p className="card-label">
                                    CURRENT STATUS
                                </p>

                                <p className="detail-value">
                                    {shipment.currentStatus.replaceAll(
                                        "_",
                                        " "
                                    )}
                                </p>

                            </div>

                            <div className="detail-box">

                                <p className="card-label">
                                    ESTIMATED DELIVERY
                                </p>

                                <p className="detail-value">

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

                                </p>

                            </div>

                        </div>

                        <div className="live-indicator">
                            <span className="live-dot"></span>
                            Live tracking enabled
                        </div>

                    </section>
                )}

            </section>

        </main>
    );
}

export default TrackingPage;