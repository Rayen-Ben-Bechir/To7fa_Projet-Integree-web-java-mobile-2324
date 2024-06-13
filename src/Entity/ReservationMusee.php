<?php

namespace App\Entity;

use App\Repository\ReservationMuseeRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ReservationMuseeRepository::class)]
class ReservationMusee
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $reservation_id;

    #[ORM\Column(type: 'datetime')]
    private $date_reservation;

    #[ORM\Column(type: 'integer')]
    private $nbr_tickets_reserves;

    #[ORM\Column(type: 'integer')]
    private $id_user;

    #[ORM\Column(type: 'integer')]
    private $id_musee;

    // Define getters and setters for properties

    public function getReservationId(): ?int
    {
        return $this->reservation_id;
    }

    public function getDateReservation(): ?\DateTimeInterface
    {
        return $this->date_reservation;
    }

    public function setDateReservation(\DateTimeInterface $date_reservation): self
    {
        $this->date_reservation = $date_reservation;
        return $this;
    }

    // Add getters and setters for other properties

    // Other methods as needed
}
