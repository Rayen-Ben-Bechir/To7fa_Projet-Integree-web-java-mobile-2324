<?php

namespace App\Service;

use DateTime;
use phpDocumentor\Reflection\Types\Integer;
use Symfony\Component\HttpFoundation\RequestStack;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;

class UtilisateurService{

    private $mailer;
    private $session;

    public function __construct(MailerInterface $mailer,RequestStack $session)
    {
        $this->mailer = $mailer;
        $this->session=$session;
    }


 


    public function sendEmail(string $subject,string $text,string $email)
    {
       
        $email = (new Email())
            ->from('rayenbenbechir8@gmail.com')
            ->to($email)
            ->subject($subject)
            ->text($text);
       //     ->html('<p>Contenu du message en HTML</p>');

       try{
        $this->mailer->send($email);
       } catch (\Exception $e) {
        dd($e->getMessage());
       }
    }










    public function generateRandomCode(int $length = 5): string
    {
        $characters = '0123456789';
        $code = '';

        for ($i = 0; $i < $length; $i++) {
            $code .= $characters[random_int(0, strlen($characters) - 1)];
        }

        return $code;
    }

    public function sendResetCodeByEmail(string $email, string $code): void
    {
        $email = (new Email())
            ->from('rayenbenbechir8@gmail.com')
            ->to($email)
            ->subject('Password Reset Code')
            ->text('Your password reset code is: ' . $code);

        $this->mailer->send($email);
    }
}
