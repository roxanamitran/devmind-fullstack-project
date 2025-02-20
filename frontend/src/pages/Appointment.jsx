import { useEffect, useState } from "react";
import {
  Col,
  Container,
  Row,
  ListGroup,
  Button,
  Modal,
  Form
} from "react-bootstrap";
import Calendar from "react-calendar/dist/cjs/Calendar.js";
import apiClient from "../api/axios";

function Appointment() {
  const [date, setDate] = useState(null);
  const [employees, setEmployees] = useState([]);
  const [error, setError] = useState(null);
  const [isDateModalOpen, setIsDateModalOpen] = useState(false);
  const [isAvailableSlotsModalOpen, setIsAvailableSlotsModalOpen] =
    useState(false);
  const [employeeId, setEmployeeId] = useState("");
  const [availableSlots, setAvailableSlots] = useState([]);
  const [selectedSlot, setSelectedSlot] = useState("");

  const queryParams = new URLSearchParams(location.search);
  const salonId = queryParams.get("salon_id");
  const salonOfferId = queryParams.get("salon_offer_id");

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const response = await apiClient.get(`/salons/${salonId}/employees`);
        console.log(response);
        setEmployees(response.data);
      } catch (err) {
        console.error("Error fetching employees:", err);
        setError("Nu am putut încărca lista de angajați.");
      }
    };

    fetchEmployees();
  }, []);

  function handleEmployeeSelection(id) {
    const dateModalValue = !isDateModalOpen;
    setIsDateModalOpen(dateModalValue);

    if (dateModalValue) {
      setEmployeeId(id);
    } else {
      setEmployeeId("");
      setIsAvailableSlotsModalOpen(false);
    }
  }

  const onDateChanged = async (newDate) => {
    setDate(newDate);

    const resp = await apiClient.post("/appointments/available-slots", {
      salonId,
      employeeId,
      date: newDate.toISOString()
    });

    if (resp.data.length > 0) {
      setSelectedSlot(resp.data[0]);
    }
    setAvailableSlots(resp.data);
    setIsAvailableSlotsModalOpen(true);
  };

  const handleSubmit = async () => {
    try {
      await apiClient.post("/appointments", {
        salon: {
          id: salonId
        },
        salonToSalonOffer: {
          id: salonOfferId
        },
        customer: {},
        employee: {
          id: employeeId
        },
        startDate: date
      });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <h1>Alege profesionistul preferat și apoi vezi când este disponibil!</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <Container>
        <Row>
          <Col md={12}>
            <ListGroup>
              {employees.map((employee) => (
                <ListGroup.Item key={employee.id} value={employee.id}>
                  <img
                    src={employee.photoUrl}
                    alt={employee.firstName}
                    style={{
                      width: "90px",
                      height: "90px"
                    }}
                  />
                  <span style={{ marginRight: "20px", marginLeft: "20px" }}>
                    {employee.firstName} {employee.lastName}
                  </span>
                  <span style={{ marginRight: "20px", marginLeft: "100px" }}>
                    <Button
                      variant="dark"
                      onClick={() => handleEmployeeSelection(employee.id)}
                    >
                      Alege angajat
                    </Button>
                  </span>
                </ListGroup.Item>
              ))}
            </ListGroup>
            <>
              {isDateModalOpen && (
                <div
                  className="modal show"
                  style={{ display: "block", position: "initial" }}
                >
                  <Modal.Dialog>
                    <Modal.Header closeButton>
                      <Modal.Title>Alege ziua disponibila</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                      <Calendar onChange={onDateChanged} value={date} />
                    </Modal.Body>

                    <Modal.Footer>
                      <Button variant="dark" onClick={handleSubmit}>
                        Trimite programarea
                      </Button>
                    </Modal.Footer>
                  </Modal.Dialog>
                </div>
              )}
              {isAvailableSlotsModalOpen && (
                <div>
                  <h1>Ore disponibile</h1>
                  <Form.Select
                    type="text"
                    value={selectedSlot}
                    onChange={(e) => setSelectedSlot(e.target.value)}
                  >
                    {availableSlots.map((availableSlot) => (
                      <option key={availableSlot} value={availableSlot}>
                        {availableSlot}
                      </option>
                    ))}
                  </Form.Select>
                </div>
              )}
            </>
          </Col>
        </Row>
      </Container>
    </>
  );
}

export default Appointment;
