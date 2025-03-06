import { useEffect, useState } from "react";
import { Form, Button, Table } from "react-bootstrap";
import apiClient from "../api/axios";
import { useNavigate, useParams } from "react-router-dom";

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
  const navigate = useNavigate();
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
      newSchedules[index].startHour = "--";
      newSchedules[index].endHour = "--";
    } else {
      newSchedules[index].startHour = "";
      newSchedules[index].endHour = "";
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

        navigate("/my_salon");
      } else {
        await apiClient.post("salons", newSalon, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
          }
        });
        navigate("/my_salon");
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
    <>
      <div>
        <Form className="addSalon">
          <Form.Group className="mb-3">
            <Form.Label>Numele salonului</Form.Label>
            <Form.Control
              type="text"
              placeholder="Introduceti numele"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Numarul de telefon</Form.Label>
            <Form.Control
              type="text"
              placeholder="Introduceti telefonul"
              value={phoneNumber}
              onChange={(e) => setPhoneNumber(e.target.value)}
            ></Form.Control>
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Email salon</Form.Label>
            <Form.Control
              type="text"
              placeholder="Introduceti emailul"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Adresa</Form.Label>
            <Form.Control
              type="text"
              placeholder="Introduceti adresa"
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
              placeholder="Introduceti orasul"
              value={city}
              onChange={(e) => setCity(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Descrierea salonului</Form.Label>
            <Form.Control
              type="text"
              placeholder="Introduceti descrierea"
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
                      value={row.startHour}
                      onChange={(e) =>
                        handleSchedulesChange(
                          index,
                          "startHour",
                          e.target.value
                        )
                      }
                      disabled={row.startHour === "--"}
                    />
                  </td>
                  <td>
                    <input
                      type="time"
                      value={row.endHour}
                      onChange={(e) =>
                        handleSchedulesChange(index, "endHour", e.target.value)
                      }
                      disabled={row.endHour === "--"}
                    />
                  </td>
                  <td>
                    <Button onClick={() => handleClosedDay(index)}>
                      {row.isClosed ? "Seteaza programul" : "Zi libera"}
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Form>
        <div className="addButton">
          <Button variant="success" size="lg" onClick={handleSubmit}>
            Salveaza modificarile facute
          </Button>
        </div>
      </div>
    </>
  );
}

export default AddSalon;
