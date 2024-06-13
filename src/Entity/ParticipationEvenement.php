<?php

namespace App\Entity;

use App\Repository\ParticipationEvenementRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ParticipationEvenementRepository::class)]
class ParticipationEvenement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $id_participation;

    #[ORM\Column(type: 'integer')]
    private $id_event;

    #[ORM\Column(type: 'integer')]
    private $id_user;

    #[ORM\Column(type: 'integer')]
    private $nombre_participation;

    #[ORM\Column(type: 'string', length: 255)]
    private $num_tel;

    // Define getters and setters for properties

    public function getIdParticipation(): ?int
    {
        return $this->id_participation;
    }

    public function getIdEvent(): int
    {
        return $this->id_event;
    }

    public function setIdEvent(int $id_event): self
    {
        $this->id_event = $id_event;
        return $this;
    }

    // Add getters and setters for other properties

    public function getNumTel(): string
    {
        return $this->num_tel;
    }

    public function setNumTel(string $num_tel): self
    {
        $this->num_tel = $num_tel;
        return $this;
    }

    // Other methods as needed
}
