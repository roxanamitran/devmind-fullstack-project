import { useEffect, useState } from "react";
import { Form, Button, Table } from "react-bootstrap";
import apiClient from "../api/axios";
import { useParams } from "react-router-dom";

function AddSalon() {
  const daysOfWeek = [
    "LUNI",
    "MARTI",
    "MIERCURI",
    "JOI",
    "VINERI",
    "SAMBATA",
    "DUMINICA"
  ];

  const [schedules, setSchedules] = useState(
    daysOfWeek.map((day) => ({
      day,
      startHour: "",
      endHour: "",
      isClosed: false
    }))
  );
  const [name, setName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [email, setEmail] = useState("");
  const [address, setAddress] = useState("");
  const [city, setCity] = useState("");
  const [photoUrl, setPhotoUrl] = useState("");
  const [description, setDescription] = useState("");
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      const fetchSalon = async () => {
        try {
          const response = await apiClient.get(`salons/${id}`);
          const salon = response.data;
          setName(salon.name);
          setPhoneNumber(salon.phoneNumber);
          setEmail(salon.email);
          setAddress(salon.address);
          setCity(salon.city);
          setPhotoUrl(salon.photoUrl);
          setDescription(salon.description);
        } catch (error) {
          console.log("Error fetching salon details", error);
        }
      };
      fetchSalon();
    }
  }, [id]);

  const handleSchedulesChange = (index, field, value) => {
    const newSchedules = [...schedules];
    newSchedules[index][field] = value;
    setSchedules(newSchedules);
  };

  const handleClosedDay = (index) => {
    const newSchedules = [...schedules];
    newSchedules[index].isClosed = !newSchedules[index].isClosed;

    if (newSchedules[index].isClosed) {
      newSchedules[index].startTime = "--";
      newSchedules[index].endTime = "--";
    } else {
      newSchedules[index].startTime = "";
      newSchedules[index].endTime = "";
    }

    setSchedules(newSchedules);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newSalon = {
      name,
      phoneNumber,
      email,
      address,
      city,
      photoUrl,
      description,
      schedules
    };

    try {
      if (id) {
        await apiClient.put(`salons/${id}`, newSalon, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
          }
        });
      } else {
        await apiClient.post("salons", newSalon, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
          }
        });
      }

      setName("");
      setPhoneNumber("");
      setEmail("");
      setAddress("");
      setCity("");
      setPhotoUrl("");
      setDescription("");
    } catch (error) {
      console.log("Error adding salon", error);
    }
  };

  return (
    <div className="form">
      <Form className="form" onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Numele salonului</Form.Label>
          <Form.Control
            type="text"
            placeholder="Introduceti numele salonului"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Numarul de telefon</Form.Label>
          <Form.Control
            type="text"
            placeholder="Introduceti numarul de telefon"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value)}
          ></Form.Control>
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Email salon</Form.Label>
          <Form.Control
            type="text"
            placeholder="Introduceti adresa de email a salonului"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Adresa</Form.Label>
          <Form.Control
            type="text"
            placeholder="Introduceti adresa salonului"
            value={address}
            onChange={(e) => setAddress(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Imaginea</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            placeholder="Introduceti url-ul imaginii"
            value={photoUrl}
            onChange={(e) => setPhotoUrl(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Orasul</Form.Label>
          <Form.Control
            type="text"
            placeholder="Introduceti orasul in care se afla salonul"
            value={city}
            onChange={(e) => setCity(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Descrierea salonului</Form.Label>
          <Form.Control
            type="text"
            placeholder="Introduceti descrierea salonului"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </Form.Group>
        <Table>
          <thead>
            <tr>
              <th>Ziua saptamanii</th>
              <th>Ora de deschidere</th>
              <th>Ora de inchidere</th>
            </tr>
          </thead>
          <tbody>
            {schedules.map((row, index) => (
              <tr key={index}>
                <td>{row.day}</td>
                <td>
                  <input
                    type="time"
                    value={row.startTime}
                    onChange={(e) =>
                      handleSchedulesChange(index, "startHour", e.target.value)
                    }
                    disabled={row.startTime === "--"}
                  />
                </td>
                <td>
                  <input
                    type="time"
                    value={row.endTime}
                    onChange={(e) =>
                      handleSchedulesChange(index, "endHour", e.target.value)
                    }
                    disabled={row.endTime === "--"}
                  />
                </td>
                <td>
                  <button onClick={() => handleClosedDay(index)}>
                    {row.isClosed ? "Seteaza programul" : "Zi libera"}
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>

        <Button variant="success" type="submit">
          Salveaza modificarile facute
        </Button>
      </Form>
    </div>
  );
}

export default AddSalon;
