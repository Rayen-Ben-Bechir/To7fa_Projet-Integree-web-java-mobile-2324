<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\RepeatedType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
#[ORM\Entity(repositoryClass: UserRepository::class)]
class User
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $userId = null;

    #[ORM\Column(type: 'string', length: 255)]
    #[Assert\NotBlank(message: 'Le nom est requis.')]
    #[Assert\Length(
        min: 3,
        minMessage: 'Le nom doit contenir au moins {{ limit }} caractères.'
    )]
    private string $userName;

    #[ORM\Column(type: 'string', length: 255)]
    #[Assert\NotBlank(message: 'Le numéro de téléphone est requis.')]
    #[Assert\Regex(
        pattern: '/^\d{8}$/',
        message: 'Le numéro de téléphone doit être composé de 8 chiffres.'
    )]
    private string $userPhone;

    #[ORM\Column(type: 'string', length: 255, unique: true)]
    #[Assert\NotBlank(message: 'L\'adresse email est requise.')]
    #[Assert\Email(message: 'L\'adresse email n\'est pas valide.')]
    #[Assert\Regex(
        pattern: '/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/',
        message: 'L\'adresse email doit avoir la forme user@example.com.'
    )]
    private string $userEmail;

    #[ORM\Column(type: 'string', length: 255)]
    #[Assert\NotBlank(message: 'Le mot de passe est requis.')]
    #[Assert\Length(
        min: 6,
        minMessage: 'Le mot de passe doit contenir au moins {{ limit }} caractères.'
    )]
    private string $userPassword;

    #[ORM\Column(type: 'string', length: 255)]
    #[Assert\NotBlank(message: 'Le sexe est requis.')]
    private string $userSexe;

    #[ORM\Column(type: 'string', length: 255)]
     private string $userRole;

    #[ORM\Column(type: 'string', length: 255)]
    private string $userImage;

    #[ORM\Column(type: 'boolean')]
    private bool $isBanned = false;

    // Getters and setters for the properties

    public function getUserId(): ?int
    {
        return $this->userId;
    }

    public function getUserName(): string
    {
        return $this->userName;
    }

    public function setUserName(string $userName): self
    {
        $this->userName = $userName;

        return $this;
    }

    // Add getters and setters for other properties

    public function getUserPhone(): string
    {
        return $this->userPhone;
    }

    public function setUserPhone(string $userPhone): self
    {
        $this->userPhone = $userPhone;

        return $this;
    }

    // Implement PasswordAuthenticatedUserInterface methods if needed

    public function getUserEmail(): string
    {
        return $this->userEmail;
    }

    public function setUserEmail(string $userEmail): self
    {
        $this->userEmail = $userEmail;
        return $this;
    }


    public function getUserPassword(): string
    {
        return $this->userPassword;
    }

    public function setUserPassword(string $userPassword): self
    {
        $this->userPassword = $userPassword;
        return $this;
    }

    public function eraseCredentials(): void
    {
        // If you need to erase any sensitive data, do it here
    }
    public function getuserRole(): ?string
    {
        return $this->userRole;
    }

    public function setuserRole(string $roles): static
    {
        $this->userRole = $roles;

        return $this;
    }  public function getUserSexe(): ?string
    {
        return $this->userSexe;
    }

    public function setUserSexe(string $userSexe): self
    {
        $this->userSexe = $userSexe;
        return $this;
    }
    public function getUserImage(): ?string
    {
        return $this->userImage;
    }

    public function setUserImage(string $userImage): self
    {
        $this->userImage = $userImage;
        return $this;
    }
    public function isBanned(): bool
    {
        return $this->isBanned;
    }
    public function setBanned(bool $isBanned): self
    {
        $this->isBanned = $isBanned;
        return $this;
    }



   }