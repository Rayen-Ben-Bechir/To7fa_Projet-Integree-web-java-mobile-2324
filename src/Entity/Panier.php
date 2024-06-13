<?php

namespace App\Entity;

use App\Repository\PanierRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: PanierRepository::class)]
class Panier
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $id_panier;

    #[ORM\Column(type: 'integer')]
    private $id_oeuvre;

    #[ORM\Column(type: 'integer')]
    private $id_user;

    // Define getters and setters for properties

    public function getIdPanier(): ?int
    {
        return $this->id_panier;
    }

    public function getIdOeuvre(): int
    {
        return $this->id_oeuvre;
    }

    public function setIdOeuvre(int $id_oeuvre): self
    {
        $this->id_oeuvre = $id_oeuvre;
        return $this;
    }

    public function getIdUser(): int
    {
        return $this->id_user;
    }

    public function setIdUser(int $id_user): self
    {
        $this->id_user = $id_user;
        return $this;
    }

    // Other methods as needed
}
