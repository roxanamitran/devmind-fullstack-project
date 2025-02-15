import { useState } from "react";
import { Form, Col, Row, Button } from "react-bootstrap";
import apiClient from "../api/axios";
import { useNavigate } from "react-router-dom";

function CreateUser() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [photoUrl, setPhotoUrl] = useState("");
  const [userType, setUserType] = useState("CUSTOMER");
  const navigate = useNavigate();

  const handleSubmit = async () => {
    const response = await apiClient.post("/users", {
      email,
      password,
      firstName,
      lastName,
      phoneNumber,
      photoUrl,
      userType
    });

    if (response != null) {
      navigate("/login");
    }
  };

  return (
    <div>
      <Form className="form">
        <h1>Creeaza-ti un cont</h1>
        <Form.Group as={Row} className="mb-3">
          <Form.Label column sm="2">
            Prenume
          </Form.Label>
          <Col sm="10">
            <Form.Control
              type="text"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} className="mb-3">
          <Form.Label column sm="2">
            Nume
          </Form.Label>
          <Col sm="10">
            <Form.Control
              type="text"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} className="mb-3">
          <Form.Label column sm="2">
            Numar de telefon
          </Form.Label>
          <Col sm="10">
            <Form.Control
              type="text"
              value={phoneNumber}
              onChange={(e) => setPhoneNumber(e.target.value)}
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} className="mb-3">
          <Form.Label column sm="2">
            Fotografie
          </Form.Label>
          <Col sm="10">
            <Form.Control
              type="text"
              placeholder="URL-ul imaginii"
              value={photoUrl}
              onChange={(e) => setPhotoUrl(e.target.value)}
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} className="mb-3">
          <Form.Label column sm="2">
            Utilizator
          </Form.Label>
          <Col sm="10">
            <Form.Select
              type="text"
              placeholder="Selectati tipul de user"
              value={userType}
              onChange={(e) => setUserType(e.target.value)}
            >
              <option value="CUSTOMER">Client</option>
              <option value="MANAGER">Manager</option>
              <option value="EMPLOYEE">Angajat</option>
            </Form.Select>
          </Col>
        </Form.Group>

        <Form.Group as={Row} className="mb-3">
          <Form.Label column sm="2">
            Email
          </Form.Label>
          <Col sm="10">
            <Form.Control
              type="email"
              placeholder="example@mail.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Col>
        </Form.Group>

        <Form.Group as={Row} className="mb-3">
          <Form.Label column sm="2">
            Parola
          </Form.Label>
          <Col sm="10">
            <Form.Control
              type="password"
              placeholder="Parola"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </Col>
        </Form.Group>
        <Button onClick={handleSubmit}>Creeaza cont</Button>
      </Form>
    </div>
  );
}

export default CreateUser;
